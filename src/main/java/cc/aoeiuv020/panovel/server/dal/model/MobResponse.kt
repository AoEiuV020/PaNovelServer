package cc.aoeiuv020.panovel.server.dal.model

import cc.aoeiuv020.panovel.server.common.ErrorCode

/**
 *
 * Created by AoEiuV020 on 2018.04.02-11:21:33.
 */
class MobResponse(
        var code: Int = ErrorCode.UNKNOWN_ERROR.code,
        var data: Any? = null
) {
    fun setError(error: ErrorCode) {
        code = error.code
    }
}