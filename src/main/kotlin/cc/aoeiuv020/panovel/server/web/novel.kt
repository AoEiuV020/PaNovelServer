package cc.aoeiuv020.panovel.server.web

import cc.aoeiuv020.panovel.server.common.BaseLoggable
import cc.aoeiuv020.panovel.server.dal.model.MobRequest
import cc.aoeiuv020.panovel.server.dal.model.MobResponse
import cc.aoeiuv020.panovel.server.dal.model.autogen.Novel
import cc.aoeiuv020.panovel.server.service.NovelService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

/**
 *
 * Created by AoEiuV020 on 2018.04.19-16:59:55.
 */
@Controller
@RequestMapping("/novel")
class NovelController : BaseLoggable() {
    @Autowired
    private lateinit var novelService: NovelService

    @PostMapping("/needRefreshNovelList")
    @ResponseBody
    fun needRefreshNovelList(@RequestBody mobRequest: MobRequest): MobResponse {
        val count: Int = mobRequest.getRealData()
        val resultNovel = novelService.needRefreshNovelList(count)
        return MobResponse.success(resultNovel)
    }

    /**
     * 小说没有更新时调用这个touch摸一下更新这本小说最后检查更新时间，
     */
    @PostMapping("/touch")
    @ResponseBody
    fun touch(@RequestBody mobRequest: MobRequest): MobResponse {
        val novel: Novel = mobRequest.getRealData()
        if (novel.site == null || novel.author == null || novel.name == null) {
            // 和2.2.2之前旧版接口路径一致，但不兼容，直接返回错误，
            return MobResponse.error()
        }
        val resultNovel = novelService.touch(novel)
        return MobResponse.success(resultNovel)
    }

    /**
     * 小说有更新时调用这个更新一下数据库，
     */
    @PostMapping("/update")
    @ResponseBody
    fun update(@RequestBody mobRequest: MobRequest): MobResponse {
        val novel: Novel = mobRequest.getRealData()
        if (novel.site == null || novel.author == null || novel.name == null) {
            // 和2.2.2之前旧版接口路径一致，但不兼容，直接返回错误，
            return MobResponse.error()
        }
        val resultNovel = novelService.uploadUpdate(novel)
        return MobResponse.success(resultNovel)
    }

    /**
     * 主要是为了拿到小说的最后检查更新时间，
     * 如果数据库没有这本小说，则会插入，但返回的小说对象就的检查更新时间就是null,
     */
    @PostMapping("/query")
    @ResponseBody
    fun query(@RequestBody mobRequest: MobRequest): MobResponse {
        val novel: Novel = mobRequest.getRealData()
        if (novel.site == null || novel.author == null || novel.name == null) {
            // 和2.2.2之前旧版接口路径一致，但不兼容，直接返回错误，
            return MobResponse.error()
        }
        val resultNovel = novelService.query(novel)
        return MobResponse.success(resultNovel)
    }

}
