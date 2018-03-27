package cc.aoeiuv020.panovel.server.web

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.collections.LinkedHashMap

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

    /**
     * 已经配置了jackson, 对象转成json,
     */
    @RequestMapping("/object")
    @ResponseBody
    fun testObject(): Student {
        return Student("AoEiuV020", 23)
    }

    data class Student(val name: String, val age: Int)

    /**
     * @return 返回的是数字，不知道怎么配置这里的Date formatter,
     */
    @RequestMapping("/date")
    @ResponseBody
    fun date(): Date {
        return Date()
    }

    /**
     * parameterMap返回的value是数组，不知道什么情况会大于一个，
     */
    @RequestMapping("/http")
    @ResponseBody
    fun http(request: HttpServletRequest, response: HttpServletResponse): Map<String, Any> {
        val map = LinkedHashMap<String, Any>()
        request.headerNames.toList().map { it to request.getHeader(it) }.toMap().let {
            map.put("headers", it)
        }
        request.parameterMap.mapValues { it.value[0] }.let {
            map.put("parameters", it)
        }
        response.setHeader("key", "value")
        response.headerNames.toList().map { it to response.getHeader(it) }.toMap().let {
            map.put("responseHeaders", it)
        }
        return map
    }

}