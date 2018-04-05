package cc.aoeiuv020.panovel.server.web

import cc.aoeiuv020.panovel.server.common.Loggable
import cc.aoeiuv020.panovel.server.common.toBean
import cc.aoeiuv020.panovel.server.common.toJson
import cc.aoeiuv020.panovel.server.dal.model.autogen.Novel
import cc.aoeiuv020.panovel.server.service.NovelService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.socket.server.standard.SpringConfigurator
import javax.websocket.OnClose
import javax.websocket.OnMessage
import javax.websocket.OnOpen
import javax.websocket.Session
import javax.websocket.server.ServerEndpoint

/**
 * Created by AoEiuV020 on 2018.04.05-08:13:21.
 */


/**
 * 负责通知小说更新的websocket，
 */
@Controller
@ServerEndpoint("/ws/update", configurator = SpringConfigurator::class)
class NotifyUpdateController : Loggable {
    @Autowired
    private lateinit var novelService: NovelService

    companion object {
        val sessions: MutableSet<Session> = mutableSetOf()
        private val bookshelfMap: MutableMap<Int, MutableSet<Session>> = mutableMapOf()

        @Synchronized
        fun getUsers(id: Int): MutableSet<Session> {
            return bookshelfMap[id]
                    ?: mutableSetOf<Session>().also { bookshelfMap[id] = it }
        }

        private fun notifyUpdate(from: Session, id: Int) {
            getUsers(id).forEach { session ->
                session.takeIf { it.isOpen }
                        ?.takeIf { it != from }
                        ?.asyncRemote
                        ?.sendText(ResponseMessage(Action.UPDATE, id.toJson()).toJson())
            }
        }
    }

    private fun update(from: Session, novel: Novel) {
        if (novelService.update(novel)) {
            notifyUpdate(from, novel.id)
        }
    }

    private fun addAll(session: Session, list: List<Int>) {
        // TODO: 顺便查一下有没有更新，
        list.forEach { id ->
            getUsers(id)
                    .add(session)
        }
    }

    private fun remove(session: Session, id: Int) {
        getUsers(id)
                .remove(session)
    }

    private fun removeAll(session: Session, list: List<Int>) {
        list.forEach { id ->
            remove(session, id)
        }
    }

    @OnOpen
    fun onOpen(session: Session) {
        sessions.add(session)
    }

    @OnClose
    fun onClose(session: Session) {
        sessions.remove(session)
    }

    @OnMessage
    fun onMessage(text: String, session: Session) {
        try {
            val message: RequestMessage = text.toBean()
            when (message.action) {
                Action.UPDATE -> update(session, message.getRealData())
                Action.BOOKSHELF_ADD -> addAll(session, message.getRealData())
                Action.BOOKSHELF_REMOVE -> removeAll(session, message.getRealData())
                Action.UNKNOWN -> {
                }
            }
        } catch (e: Exception) {
            // 这里不捕获的话整个websocket连接就断了，
            e.printStackTrace()
        }
    }

}


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