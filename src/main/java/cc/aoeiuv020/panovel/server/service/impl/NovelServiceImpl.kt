package cc.aoeiuv020.panovel.server.service.impl

import cc.aoeiuv020.panovel.server.common.Loggable
import cc.aoeiuv020.panovel.server.common.debug
import cc.aoeiuv020.panovel.server.common.info
import cc.aoeiuv020.panovel.server.dal.mapper.autogen.NovelMapper
import cc.aoeiuv020.panovel.server.dal.model.autogen.Novel
import cc.aoeiuv020.panovel.server.dal.model.autogen.NovelExample
import cc.aoeiuv020.panovel.server.service.NovelService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Created by AoEiuV020 on 2018.04.05-10:07:57.
 */
@Service("novelService")
class NovelServiceImpl : NovelService, Loggable {
    @Autowired
    private lateinit var novelMapper: NovelMapper

    override fun update(novel: Novel): Boolean {
        logger.info { "update ${novel.id}: ${novel.updateTime}" }
        // 这个按里说不会查不到，
        val result = novelMapper.selectByPrimaryKey(novel.id) ?: return false
        // 如果数据库里的比较新，就不通知更新，
        // 有的小说可能不知道更新时间，那就是默认，就会相等，对比chaptersCount,
        if (result.updateTime > novel.updateTime) {
            return false
        } else if (result.updateTime == novel.updateTime
                && result.chaptersCount >= novel.chaptersCount) {
            return false
        }
        result.chaptersCount = novel.chaptersCount
        result.updateTime = novel.updateTime
        return novelMapper.updateByPrimaryKeySelective(result) == 1
    }

    override fun queryIds(novelList: List<Novel>): List<Int> {
        logger.info { "queryIds list size = ${novelList.size}" }
        return novelList.map { novel ->
            // TODO: 检查两个not null,
            val e = NovelExample()
            e.or()
                    .andRequesterExtraEqualTo(novel.requesterExtra)
                    .andRequesterTypeEqualTo(novel.requesterType)

            // 设置和unique，按理说结果最多一个，
            val resultList = novelMapper.selectByExample(e)
            if (resultList.isEmpty()) {
                novelMapper.insertSelective(novel)
                novel
            } else {
                resultList[0]
            }.id.also { id ->
                logger.debug { "queryIds $id -> ${novel.requesterExtra}" }
            }
        }
    }
}