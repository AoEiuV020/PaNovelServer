package cc.aoeiuv020.panovel.server.web

import cc.aoeiuv020.panovel.server.common.ErrorCode
import cc.aoeiuv020.panovel.server.dal.model.MobResponse
import cc.aoeiuv020.panovel.server.dal.model.autogen.User
import cc.aoeiuv020.panovel.server.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody

/**
 * Created by AoEiuV020 on 2018.04.02-11:17:53.
 */
@Controller
class UserTestController {
    @Autowired
    private lateinit var userService: UserService

    @PostMapping("/user/add")
    @ResponseBody
    fun add(@RequestBody user: User): MobResponse {
        val response = MobResponse()
        try {
            val count = userService.add(user)
            response.setError(if (count == 1) ErrorCode.SUCCESS else ErrorCode.UNKNOWN_ERROR)
            response.data = count
        } catch (e: Exception) {
            response.setError(ErrorCode.UNKNOWN_ERROR)
            e.printStackTrace()
        }
        return response
    }

    @PostMapping("/user/remove")
    @ResponseBody
    fun remove(@RequestBody user: User): MobResponse {
        val response = MobResponse()
        try {
            user.id?.let {
                val count = userService.remove(user)
                response.setError(if (count == 1) ErrorCode.SUCCESS else ErrorCode.UNKNOWN_ERROR)
                response.data = count
            } ?: response.setError(ErrorCode.UNKNOWN_ERROR)
        } catch (e: Exception) {
            response.setError(ErrorCode.UNKNOWN_ERROR)
            e.printStackTrace()
        }
        return response
    }
}