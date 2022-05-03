package dev.eshilov.mementomemobot.telegram.dto

data class Update(
    val updateId: Long,
    val message: Message?,
    val callbackQuery: CallbackQuery?
)
