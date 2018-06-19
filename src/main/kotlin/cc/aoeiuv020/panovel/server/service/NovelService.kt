package cc.aoeiuv020.panovel.server.service

import cc.aoeiuv020.panovel.server.dal.model.autogen.Novel

/**
 *
 * Created by AoEiuV020 on 2018.04.05-09:13:02.
 */
interface NovelService {
    /**
     * 通知服务器这本小说有更新，
     *
     * @return 如果确实传入的记录比数据里的更新就返回true,
     */
    fun uploadUpdate(novel: Novel): Boolean

    /**
     * 需要更新的小说列表，
     * 就是最后检查过更新小说，
     *
     * @param count 返回小说数量，最多500,
     */
    fun needRefreshNovelList(count: Int): List<Novel>

    /**
     * 用户打开小说列表时对整个列表调用，查询是否有更新，
     */
    fun queryList(novelList: List<Novel>): List<Novel>

    /**
     * 有用户刷新但刷新结果是没有更新时调用，
     */
    fun touch(novel: Novel): Boolean

    fun minVersion(): String
}