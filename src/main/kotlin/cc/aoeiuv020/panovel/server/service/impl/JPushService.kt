package cc.aoeiuv020.panovel.server.service.impl

import cc.aoeiuv020.panovel.server.common.md5
import cc.aoeiuv020.panovel.server.common.toJson
import cc.aoeiuv020.panovel.server.dal.mapper.autogen.NovelMapper
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
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

@Component
@PropertySource("classpath:jpush.properties")
class JPushConfig {
    @Value("\${appKey}")
    lateinit var appKey: String
    @Value("\${masterSecret}")
    lateinit var masterSecret: String
}

@Component
class JPushComponent {
    @Autowired
    private lateinit var config: JPushConfig

    @Bean
    fun client(): JPushClient {
        return JPushClient(config.masterSecret, config.appKey, null, ClientConfig.getInstance())
    }
}

@Service("pushService")
class JPushService : PushService {
    companion object {
        val LOG = LoggerFactory.getLogger(JPushService::class.java)
    }


    @Autowired
    private lateinit var novelMapper: NovelMapper
    @Autowired
    private lateinit var jPushClient: JPushClient

    override fun pushUpdate(novel: Novel): Boolean {
        val payload = buildPayload(novel)
        try {
            val result = jPushClient.sendPush(payload)
            LOG.info("Got result - $result")
            return result.isResultOK
        } catch (e: APIConnectionException) {
            LOG.error("Connection error. Should retry later. ", e)
            LOG.error("Sendno: " + payload.sendno)

        } catch (e: APIRequestException) {
            if (e.errorCode == 1011) {
                // cannot find user by this audience,
                LOG.info(e.errorMessage)
                // 没人订阅的小说，直接从服务器删除，可能导致某些小说反复删除后被搜索出来，无所谓了，
                LOG.info("delete: <${novel.run { "$site.$author.$name" }}>")
                novelMapper.deleteByPrimaryKey(novel.id)
            } else {
                LOG.error("Error response from JPush server. Should review and fix it. ", e)
                LOG.info("HTTP Status: " + e.status)
                LOG.info("Error Code: " + e.errorCode)
                LOG.info("Error Message: " + e.errorMessage)
                LOG.info("Msg ID: " + e.msgId)
                LOG.error("Sendno: " + payload.sendno)
            }
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