package cc.aoeiuv020.panovel.server.common

import cc.aoeiuv020.panovel.server.dal.model.autogen.Novel
import java.security.MessageDigest

/**
 * 计算md5充当推送的tag,
 * 两端计算结果必需一致，
 *
 * Created by AoEiuV020 on 2018.04.17-13:01:51.
 */
fun String.md5(): String {
    val md = MessageDigest.getInstance("MD5")
    val digested = md.digest(toByteArray())
    return digested.joinToString("") {
        String.format("%02x", it)
    }
}

// 算md5以确保长度小于，40, 极光推送限制tag长度40,
fun Novel.md5(): String = "$site.$author.$name".md5()
