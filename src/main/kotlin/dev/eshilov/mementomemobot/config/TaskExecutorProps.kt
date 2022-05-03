package dev.eshilov.mementomemobot.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "app.task-executor")
data class TaskExecutorProps(
    val maxPoolSize: Int,
    val queueCapacity: Int,
    val keepAliveSeconds: Int
)
