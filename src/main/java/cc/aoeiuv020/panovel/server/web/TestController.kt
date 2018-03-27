package cc.aoeiuv020.panovel.server.web

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

/**
 *
 * Created by AoEiuV020 on 2018.03.27-04:30:25.
 */
@Controller
class TestController {
    @RequestMapping("/kotlin")
    @ResponseBody
    fun kotlin(): String {
        return "kotlin\n"
    }
}