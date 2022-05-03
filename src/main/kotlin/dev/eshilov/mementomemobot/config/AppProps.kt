package dev.eshilov.mementomemobot.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "app")
data class AppProps(
    val batchSize: Int,
    val timeoutSeconds: Int,
    val botToken: String,
    val maxSubmitAttempts: Int
)
