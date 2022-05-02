package dev.eshilov.mementomemobot.dto

data class MessageEntity(
    val type: String,
    val offset: Int,
    val length: Int
)
