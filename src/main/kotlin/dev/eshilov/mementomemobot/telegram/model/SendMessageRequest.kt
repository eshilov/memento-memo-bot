package dev.eshilov.mementomemobot.telegram.model

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class SendMessageRequest(
    val chatId: Long,
    val text: String,
    val replyMarkup: InlineKeyboardMarkup? = null
)
