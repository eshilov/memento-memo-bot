package dev.eshilov.mementomemobot.telegram.dto

data class MessageEntity(
    val type: String,
    val offset: Int,
    val length: Int
)
