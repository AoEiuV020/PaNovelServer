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

/**
 * Created by AoEiuV020 on 2018.04.05-10:07:57.
 * TODO: 好多需要行锁，
 */
@Service("novelService")
class NovelServiceImpl : NovelService, BaseLoggable() {
    @Autowired
    private lateinit var novelMapper: NovelMapper
    @Autowired
    private lateinit var pushService: PushService

    override fun query(novel: Novel): Novel {
        logger.info { "query ${novel.toJson()}" }
        requireNotNull(novel.requesterExtra)
        requireNotNull(novel.requesterType)
        return queryOrInsert(novel)
    }

    override fun needRefreshNovelList(count: Int): List<Novel> {
        logger.info { "needRefreshNovelList count: $count" }
        val e = NovelExample().apply {
            orderByClause = "modify_time desc limit $count"
        }
        return novelMapper.selectByExample(e)
    }

    override fun uploadUpdate(novel: Novel): Boolean {
        logger.info { "uploadUpdate ${novel.toJson()}" }
        requireNotNull(novel.requesterExtra)
        requireNotNull(novel.requesterType)
        requireNotNull(novel.chaptersCount)
//        requireNotNull(novel.updateTime)
        return updateActual(novel)
    }

    /**
     * 更新小说，
     *
     * @return 返回是否真的是有更新，并成功发出推送，
     */
    private fun updateActual(novel: Novel): Boolean {
        logger.debug { "updateActral ${novel.toJson()}" }
        val result = queryOrInsert(novel)
        if (result === novel) {
            return false
        }
        // 如果数据库里的比较新，就不通知更新，
        // 有的小说可能不知道更新时间，那就是默认，就会相等，对比chaptersCount,
        if (novel.updateTime == null) {
            if (result.chaptersCount >= novel.chaptersCount) {
                return false
            }
        } else {
            if (result.updateTime > novel.updateTime) {
                return false
            } else if (result.updateTime == novel.updateTime
                    && result.chaptersCount >= novel.chaptersCount) {
                return false
            }
        }
        // 这里是确实需要更新的情况，
        result.chaptersCount = novel.chaptersCount
        result.updateTime = novel.updateTime
        return novelMapper.updateByPrimaryKeySelective(result) == 1
                && pushService.pushUpdate(novel)
    }

    private fun queryActual(novel: Novel): Novel? {
        // 第一次上传这本书就会查不到，
        val e = NovelExample().apply {
            or().andRequesterExtraEqualTo(novel.requesterExtra)
                    .andRequesterTypeEqualTo(novel.requesterType)
        }
        return novelMapper.selectByExample(e).firstOrNull()
    }

    private fun queryOrInsert(novel: Novel): Novel {
        return queryActual(novel)
                ?: novel.also {
                    novelMapper.insert(novel)
                }
    }
}
