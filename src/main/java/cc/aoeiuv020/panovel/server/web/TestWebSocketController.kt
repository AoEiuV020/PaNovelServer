package cc.aoeiuv020.panovel.server.web

import javax.websocket.OnClose
import javax.websocket.OnMessage
import javax.websocket.OnOpen
import javax.websocket.Session
import javax.websocket.server.ServerEndpoint

/**
 * Created by AoEiuV020 on 2018.03.28-05:07:47.
 */
@ServerEndpoint("/echo")
class TestWebSocketController {
    @OnMessage
    fun onMessage(message: String, session: Session) {
        session.asyncRemote.sendText(message)
    }
}

/**
 * 聊天室，
 * session.getOpenSessions不行，只有一个session，
 * 好像是tomcat的bug,
 * https://stackoverflow.com/questions/38268369/tomcat-8-getopensessions-not-returning-all-sessions-for-end-point
 */
@ServerEndpoint("/chat")
class ChatWebSocketController {
    companion object {
        val sessions: MutableSet<Session> = mutableSetOf()
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
    fun onMessage(message: String) {
        broadcast(message)
    }

    private fun broadcast(message: String) {
        sessions.forEach {
            it.takeIf(Session::isOpen)?.asyncRemote?.sendText(message)
        }
    }
}
