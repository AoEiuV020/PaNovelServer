package cc.aoeiuv020.panovel.server.web

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created by AoEiuV020 on 2018.04.20-15:21:34.
 */
@RestController
@RequestMapping("/test")
class TestController {
    @RequestMapping("/hello")
    fun hello() = "Hello"
}