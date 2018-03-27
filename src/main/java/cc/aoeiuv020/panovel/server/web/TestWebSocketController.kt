package cc.aoeiuv020.panovel.server.web

import javax.websocket.OnMessage
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
