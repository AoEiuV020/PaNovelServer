package cc.aoeiuv020.panovel.server.service

import cc.aoeiuv020.panovel.server.dal.model.autogen.Novel

/**
 *
 * Created by AoEiuV020 on 2018.04.17-14:40:36.
 */
interface PushService {
    fun pushUpdate(novel: Novel): Boolean
}