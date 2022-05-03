package dev.eshilov.mementomemobot.telegram.dto

data class AnswerCallbackQueryRequest(
    val callbackQueryId: String,
    val text: String?
)
