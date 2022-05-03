package dev.eshilov.mementomemobot.config

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SNAKE_CASE
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.TaskExecutor
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.web.client.RestTemplate

@Configuration
@EnableConfigurationProperties(AppProps::class, TaskExecutorProps::class)
class AppConfig(private val taskExecutorProps: TaskExecutorProps) {

    @Bean
    fun restTemplate(): RestTemplate {
        val restTemplate = RestTemplate()
        val converter = restTemplate.messageConverters.find {
            it is MappingJackson2HttpMessageConverter
        }!!
        val jacksonConverter = converter as MappingJackson2HttpMessageConverter
        jacksonConverter.objectMapper.propertyNamingStrategy = SNAKE_CASE
        return restTemplate
    }

    @Bean
    fun processingTaskExecutor(): TaskExecutor {
        val executor = ThreadPoolTaskExecutor()
        executor.maxPoolSize = taskExecutorProps.maxPoolSize
        executor.keepAliveSeconds = taskExecutorProps.keepAliveSeconds
        executor.setQueueCapacity(taskExecutorProps.queueCapacity)
        return executor
    }
}