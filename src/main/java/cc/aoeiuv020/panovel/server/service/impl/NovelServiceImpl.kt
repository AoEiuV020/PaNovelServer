package cc.aoeiuv020.panovel.server.service.impl

import cc.aoeiuv020.panovel.server.common.BaseLoggable
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
 */
@Service("novelService")
class NovelServiceImpl : NovelService, BaseLoggable() {
    @Autowired
    private lateinit var novelMapper: NovelMapper
    @Autowired
    private lateinit var pushService: PushService

    override fun uploadUpdate(novel: Novel): Boolean {
        logger.info { "uploadUpdate ${novel.toJson()}" }
        // TODO: 这里需要行锁，
        // 第一次上传这本书就会查不到，
        val e = NovelExample().apply {
            or().andRequesterExtraEqualTo(novel.requesterExtra)
                    .andRequesterTypeEqualTo(novel.requesterType)
        }
        val result = novelMapper.selectByExample(e).firstOrNull()
        if (result != null) {
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
        novelMapper.insert(novel)
        return true
    }
}
