package cc.aoeiuv020.panovel.server.web

import org.hamcrest.Matchers.equalTo
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 * Created by AoEiuV020 on 2018.04.20-15:38:59.
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class TestControllerTest {
    @Autowired
    private lateinit var mvc: MockMvc

    @Test
    fun hello() {
        val request = get("/test/hello")
        mvc.perform(request)
                .andExpect(status().isOk)
                .andExpect(content().string(equalTo("Hello")))
    }
}