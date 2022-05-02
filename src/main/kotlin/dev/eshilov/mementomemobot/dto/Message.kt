package dev.eshilov.mementomemobot.dto

data class Message(
    val messageId: Long,
    val from: User,
    val chat: Chat,
    val text: String?,
    val entities: List<MessageEntity>?
)
