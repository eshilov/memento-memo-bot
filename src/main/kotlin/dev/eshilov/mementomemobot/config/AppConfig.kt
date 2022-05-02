package dev.eshilov.mementomemobot.config

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SNAKE_CASE
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate

@Configuration
class AppConfig {

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
}