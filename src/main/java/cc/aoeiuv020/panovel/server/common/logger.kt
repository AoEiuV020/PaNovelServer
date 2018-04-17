package cc.aoeiuv020.panovel.server.common

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Created by AoEiuV020 on 2018.04.05-12:28:11.
 */
interface Loggable {
    val logger: Logger get() = LoggerFactory.getLogger(this.javaClass)
}

abstract class BaseLoggable : Loggable {
    override val logger: Logger = LoggerFactory.getLogger(this.javaClass)
}

/**
 * 定义一系列拓展，
 * 主要就是先判断再执行lambda,
 */

inline fun Logger.trace(message: () -> Any?) {
    if (isTraceEnabled) {
        trace("{}", message().toString())
    }
}

inline fun Logger.debug(message: () -> Any?) {
    if (isDebugEnabled) {
        debug("{}", message().toString())
    }
}

inline fun Logger.info(message: () -> Any?) {
    if (isInfoEnabled) {
        info("{}", message().toString())
    }
}

inline fun Logger.warn(message: () -> Any?) {
    if (isWarnEnabled) {
        warn("{}", message().toString())
    }
}

inline fun Logger.error(message: () -> Any?) {
    if (isErrorEnabled) {
        error("{}", message().toString())
    }
}

inline fun Logger.error(e: Throwable, message: () -> Any?) {
    if (isErrorEnabled) {
        error(message().toString(), e)
    }
}
