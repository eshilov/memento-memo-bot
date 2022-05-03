package dev.eshilov.mementomemobot.telegram.model

data class CallbackQuery(
    val id: String,
    val from: User,
    val message: Message?,
    val data: String?
)
