package dev.eshilov.mementomemobot.telegram.dto

data class DeleteMessageRequest(
    val chatId: Long,
    val messageId: Long
)
