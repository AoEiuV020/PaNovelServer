package cc.aoeiuv020.panovel.server.service.impl

import cc.aoeiuv020.panovel.server.common.toJson
import cc.aoeiuv020.panovel.server.dal.model.autogen.Novel
import cn.jiguang.common.ClientConfig
import cn.jpush.api.JPushClient
import cn.jpush.api.push.model.Message
import cn.jpush.api.push.model.Platform
import cn.jpush.api.push.model.PushPayload
import cn.jpush.api.push.model.audience.Audience
import org.junit.Test

/**
 * Created by AoEiuV020 on 2018.06.05-14:04:50.
 */
class JPushServiceTest {
    @Test
    fun pushUpdate() {
        println("push")
        val client = JPushClient("", "c4d000bcea91fa33a514f72b", null, ClientConfig.getInstance())
        val novel = Novel().apply {
            site = "飘天文学"
            author = "我吃西红柿"
            name = "飞剑问道"
            detail = "8/8952"
            chaptersCount = 419
        }
        client.sendPush(buildPayload(novel))
    }

    private fun buildPayload(novel: Novel): PushPayload {
        val audience = Audience.alias("debug")
        val message = Message.newBuilder()
                .setMsgContent("msgContent")
                .addExtra("novel", novel.toJson())
                .build()
        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(audience)
                .setMessage(message)
                .build()
    }

}