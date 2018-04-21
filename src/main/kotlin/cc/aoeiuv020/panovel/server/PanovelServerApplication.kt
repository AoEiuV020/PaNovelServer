package cc.aoeiuv020.panovel.server

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@MapperScan(basePackages = ["cc.aoeiuv020.panovel.server.dal.mapper.autogen"])
class PanovelServerApplication

fun main(args: Array<String>) {
    runApplication<PanovelServerApplication>(*args)
}
