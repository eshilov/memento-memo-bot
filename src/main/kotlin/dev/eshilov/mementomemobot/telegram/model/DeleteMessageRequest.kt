package dev.eshilov.mementomemobot.telegram.model

data class DeleteMessageRequest(
    val chatId: Long,
    val messageId: Long
)
