package cc.aoeiuv020.panovel.server.web

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

/**
 *
 * Created by AoEiuV020 on 2018.04.10-16:51:15.
 */
@Controller
@RequestMapping("/user")
class UserController {
    @RequestMapping("/hello")
    @ResponseBody
    fun hello(): String {
        return "hello"
    }
}
