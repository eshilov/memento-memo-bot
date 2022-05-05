package dev.eshilov.mementomemobot

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
class MementoMemoBotApplicationTests {

	@Test
	fun contextLoads() {
	}

}
