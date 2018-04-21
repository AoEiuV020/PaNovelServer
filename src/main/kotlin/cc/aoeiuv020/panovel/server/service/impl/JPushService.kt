package cc.aoeiuv020.panovel.server.service.impl

import cc.aoeiuv020.panovel.server.common.md5
import cc.aoeiuv020.panovel.server.common.toJson
import cc.aoeiuv020.panovel.server.dal.model.autogen.Novel
import cc.aoeiuv020.panovel.server.service.PushService
import cn.jiguang.common.ClientConfig
import cn.jiguang.common.resp.APIConnectionException
import cn.jiguang.common.resp.APIRequestException
import cn.jpush.api.JPushClient
import cn.jpush.api.push.model.Message
import cn.jpush.api.push.model.Platform
import cn.jpush.api.push.model.PushPayload
import cn.jpush.api.push.model.audience.Audience
import org.slf4j.LoggerFactory
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import java.util.*


@Service("pushService")
class JPushService : PushService {
    companion object {
        val LOG = LoggerFactory.getLogger(JPushService::class.java)
        private val properties = Properties().apply {
            ClassPathResource("jpush.properties").file.inputStream().use {
                load(it)
            }
        }
        private val appKey = properties.getProperty("appKey")
        private val masterSecret = properties.getProperty("masterSecret")
    }

    var clientConfig = ClientConfig.getInstance()
    var jpushClient = JPushClient(masterSecret, appKey, null, clientConfig)
    override fun pushUpdate(novel: Novel): Boolean {
        val payload = buildPayload(novel)
        try {
            val result = jpushClient.sendPush(payload)
            LOG.info("Got result - $result")
            return result.isResultOK
        } catch (e: APIConnectionException) {
            LOG.error("Connection error. Should retry later. ", e)
            LOG.error("Sendno: " + payload.sendno)

        } catch (e: APIRequestException) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e)
            LOG.info("HTTP Status: " + e.status)
            LOG.info("Error Code: " + e.errorCode)
            LOG.info("Error Message: " + e.errorMessage)
            LOG.info("Msg ID: " + e.msgId)
            LOG.error("Sendno: " + payload.sendno)
        }
        return false

    }

    private fun buildPayload(novel: Novel): PushPayload {
        val audience = Audience.tag(novel.md5())
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