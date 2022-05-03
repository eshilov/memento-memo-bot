package dev.eshilov.mementomemobot.telegram.model

data class MessageEntity(
    val type: String,
    val offset: Int,
    val length: Int
)
