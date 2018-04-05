package cc.aoeiuv020.panovel.server.web.common

import cc.aoeiuv020.panovel.server.dal.model.MobResponse
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(Exception::class)
    @ResponseBody
    fun handleException(e: Exception): MobResponse {
        e.printStackTrace()
        return MobResponse.error()
    }

}
