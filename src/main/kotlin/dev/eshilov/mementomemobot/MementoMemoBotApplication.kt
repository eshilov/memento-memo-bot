package dev.eshilov.mementomemobot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MementoMemoBotApplication

fun main(args: Array<String>) {
	runApplication<MementoMemoBotApplication>(*args)
}
