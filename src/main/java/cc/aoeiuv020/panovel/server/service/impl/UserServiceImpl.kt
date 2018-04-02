package cc.aoeiuv020.panovel.server.service.impl

import cc.aoeiuv020.panovel.server.dal.mapper.autogen.UserMapper
import cc.aoeiuv020.panovel.server.dal.model.autogen.User
import cc.aoeiuv020.panovel.server.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Created by AoEiuV020 on 2018.04.02-11:39:41.
 */
@Service("userService")
class UserServiceImpl : UserService {
    @Autowired
    private lateinit var userMapper: UserMapper

    override fun add(user: User) = userMapper.insert(user)

    override fun remove(user: User) = user.id?.let {
        userMapper.deleteByPrimaryKey(it)
    } ?: 0
}