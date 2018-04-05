package cc.aoeiuv020.panovel.server.web

import cc.aoeiuv020.panovel.server.dal.model.MobRequest
import cc.aoeiuv020.panovel.server.dal.model.MobResponse
import cc.aoeiuv020.panovel.server.dal.model.autogen.Novel
import cc.aoeiuv020.panovel.server.service.NovelService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody

/**
 * Created by AoEiuV020 on 2018.04.05-10:31:26.
 */
@Controller("/bookshelf")
class BookshelfController {
    @Autowired
    private lateinit var novelService: NovelService

    @PostMapping("/queryIds")
    @ResponseBody
    fun queryIds(@RequestBody mobRequest: MobRequest): MobResponse {
        val novelList: List<Novel> = mobRequest.getRealData()
        val ids = novelService.queryIds(novelList)
        return MobResponse.success(ids)
    }
}