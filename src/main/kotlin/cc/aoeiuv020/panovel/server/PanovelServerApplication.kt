package cc.aoeiuv020.panovel.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PanovelServerApplication

fun main(args: Array<String>) {
    runApplication<PanovelServerApplication>(*args)
}
