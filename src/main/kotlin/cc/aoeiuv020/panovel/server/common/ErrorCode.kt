package cc.aoeiuv020.panovel.server.common

/**
 * Created by AoEiuV020 on 2018.04.02-11:56:23.
 */
enum class ErrorCode(val code: Int) {
    SUCCESS(200),
    OLD_VERSION_NOT_SUPPORT(500),
    UNKNOWN_ERROR(-1)
}