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
 * Created by AoEiuV020 on 2018.04.10-16:43:49.
 */
@Deprecated("换路径了，", replaceWith = ReplaceWith("/novel/update"))
@Controller
@RequestMapping("/update")
class UpdateController : BaseLoggable() {
    @Autowired
    private lateinit var novelService: NovelService

    @PostMapping("/upload")
    @ResponseBody
    fun upload(@RequestBody mobRequest: MobRequest): MobResponse {
        val novel: Novel = mobRequest.getRealData()
        val resultNovel = novelService.uploadUpdate(novel)
        return MobResponse.success(resultNovel)
    }
}
