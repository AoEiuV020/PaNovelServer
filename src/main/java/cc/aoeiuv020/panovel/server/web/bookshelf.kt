package cc.aoeiuv020.panovel.server.web

import cc.aoeiuv020.panovel.server.service.NovelService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

/**
 * Created by AoEiuV020 on 2018.04.05-10:31:26.
 */
@Controller
@RequestMapping("/bookshelf")
class BookshelfController {
    @Autowired
    private lateinit var novelService: NovelService
}