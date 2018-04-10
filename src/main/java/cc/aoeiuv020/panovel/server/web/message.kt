package cc.aoeiuv020.panovel.server.web

import cc.aoeiuv020.panovel.server.common.toBean

/**
 * Created by AoEiuV020 on 2018.04.06-10:43:57.
 */

class RequestMessage(
        val action: Action = Action.UNKNOWN,
        val data: String = "{}"
) {
    inline fun <reified T> getRealData(): T {
        return data.toBean()
    }
}

class ResponseMessage(
        val action: Action = Action.UNKNOWN,
        val data: String = "{}"
)


enum class Action {
    UPDATE,
    BOOKSHELF_ADD, BOOKSHELF_REMOVE,
    UNKNOWN,
}
