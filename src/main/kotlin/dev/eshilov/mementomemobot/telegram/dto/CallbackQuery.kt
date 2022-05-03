package dev.eshilov.mementomemobot.telegram.dto

data class CallbackQuery(
    val id: String,
    val from: User,
    val message: Message?,
    val data: String?
)
