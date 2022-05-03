package dev.eshilov.mementomemobot.telegram.model

data class Update(
    val updateId: Long,
    val message: Message?,
    val callbackQuery: CallbackQuery?
)
