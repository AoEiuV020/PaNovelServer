package cc.aoeiuv020.panovel.server.service

import cc.aoeiuv020.panovel.server.dal.model.autogen.User

/**
 * Created by AoEiuV020 on 2018.04.02-11:38:41.
 */
interface UserService {
    fun add(user: User): Int
    fun remove(user: User): Int
}