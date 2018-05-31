package cc.aoeiuv020.panovel.server.service.impl

import cc.aoeiuv020.panovel.server.common.BaseLoggable
import cc.aoeiuv020.panovel.server.common.debug
import cc.aoeiuv020.panovel.server.common.info
import cc.aoeiuv020.panovel.server.common.toJson
import cc.aoeiuv020.panovel.server.dal.mapper.autogen.NovelMapper
import cc.aoeiuv020.panovel.server.dal.model.autogen.Novel
import cc.aoeiuv020.panovel.server.dal.model.autogen.NovelExample
import cc.aoeiuv020.panovel.server.service.NovelService
import cc.aoeiuv020.panovel.server.service.PushService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.atomic.AtomicReference

/**
 * Created by AoEiuV020 on 2018.04.05-10:07:57.
 * TODO: 好多需要行锁，
 */
@Service("novelService")
class NovelServiceImpl : NovelService, BaseLoggable() {
    companion object {
        /**
         * 记录当前表里刷新时间最新一条的时间，用于判断是否完整刷了一遍，
         * 初始化为当前时间，因为之后刷新的都在这时间之后，
         */
        val refreshLatest: AtomicReference<Date> = AtomicReference(Date())
        /**
         * 表示刷新到哪个时间了，完整刷一遍前不重复刷新失败了的，
         * 记录每次返回的待刷新列表最新一条的刷新时间，下次从这条开始，
         */
        val refreshNow: AtomicReference<Date> = AtomicReference(Date(0))
    }

    @Autowired
    private lateinit var novelMapper: NovelMapper
    @Autowired
    private lateinit var pushService: PushService

    override fun query(novel: Novel): Novel {
        logger.debug { "query ${novel.toJson()}" }
        return queryOrInsert(novel)
    }

    override fun touch(novel: Novel): Boolean {
        logger.debug { "touch ${novel.toJson()}" }
        return updateActual(novel)
    }

    override fun needRefreshNovelList(count: Int): List<Novel> {
        logger.info { "needRefreshNovelList count: $count, refreshTime: <${refreshLatest.get()}, ${refreshNow.get()}>" }
        require(count < 500)
        return novelMapper.selectByExample(NovelExample().apply {
            orderByClause = "check_update_time asc limit $count"
            or().andCheckUpdateTimeGreaterThan(refreshNow.get())
        }).also {
            it.lastOrNull()?.checkUpdateTime?.also { nt ->
                val lt = refreshLatest.get()
                if (nt > lt) {
                    // 如果取出的最后一个刷新时间大于保存的整张表最新刷新时间，
                    // 表示刷完一遍，于是重置当前时间，从0开始，
                    refreshNow.set(Date(0))
                    refreshLatest.set(latestModifyTime())
                } else {
                    refreshNow.set(nt)
                }
            }
        }
    }

    private fun latestModifyTime(): Date {
        return novelMapper.selectByExample(NovelExample().apply {
            orderByClause = "modify_time desc limit 1"
        }).firstOrNull()?.checkUpdateTime ?: Date(0)
    }

    override fun uploadUpdate(novel: Novel): Boolean {
        logger.info { "uploadUpdate ${novel.toJson()}" }
        return updateActual(novel)
    }

    private fun hasUpdate(novel: Novel): Boolean {
        val result = queryOrInsert(novel)
        if (result === novel) {
            return false
        }
        // 有的小说可能不知道更新时间，那就是默认，未必会相等，时区没考虑,
        // 所以只判断章节数，
        return novel.chaptersCount ?: 0 > result.chaptersCount
    }

    /**
     * 更新小说，
     * 如果比数据库里的新，也就是真的有更新，就推到极光，
     * 否则只更新数据库，
     * 也就是一定更新数据库，这样不怕无效更新，反正下次就覆盖了，
     *
     * @return 返回是否真的是有更新并成功更新数据库，
     */
    private fun updateActual(novel: Novel): Boolean {
        val hasUpdate = hasUpdate(novel)
        novel.checkUpdateTime = Date()
        if (hasUpdate) {
            pushService.pushUpdate(novel)
        }
        return novelMapper.updateByPrimaryKeySelective(novel) == 1 && hasUpdate
    }

    /**
     * 查询，顺便把查出来的id赋值传入的novel,
     * @param novel 可以没有id,
     */
    private fun queryActual(novel: Novel): Novel? {
        // 第一次上传这本书就会查不到，
        novel.id?.let {
            return novelMapper.selectByPrimaryKey(it)
        }
        val e = NovelExample().apply {
            or().andSiteEqualTo(novel.site)
                    .andAuthorEqualTo(novel.author)
                    .andNameEqualTo(novel.name)
        }
        return novelMapper.selectByExample(e).firstOrNull()?.also {
            novel.id = it.id
        }
    }

    private fun queryOrInsert(novel: Novel): Novel {
        return queryActual(novel)
                ?: novel.also {
                    novelMapper.insertSelective(novel)
                }
    }
}
