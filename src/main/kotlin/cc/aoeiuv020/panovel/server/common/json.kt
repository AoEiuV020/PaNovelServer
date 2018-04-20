package cc.aoeiuv020.panovel.server.common

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature

/**
 *
 * Created by AoEiuV020 on 2018.04.05-10:35:13.
 */
val objectMapper = ObjectMapper()
        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)

inline fun <reified T> getTypeReference() = object : TypeReference<T>() {}
inline fun <reified T> String.toBean() = objectMapper.readValue<T>(this, getTypeReference<T>())!!
fun Any.toJson() = objectMapper.writeValueAsString(this)!!
