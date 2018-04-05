package cc.aoeiuv020.panovel.server.common

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper

/**
 * Created by AoEiuV020 on 2018.04.05-10:35:13.
 */
val objectMapper = ObjectMapper()

inline fun <reified T> getTypeReference() = object : TypeReference<T>() {}
inline fun <reified T> String.toBean() = objectMapper.readValue<T>(this, getTypeReference<T>())!!
fun Any.toJson() = objectMapper.writeValueAsString(this)!!
