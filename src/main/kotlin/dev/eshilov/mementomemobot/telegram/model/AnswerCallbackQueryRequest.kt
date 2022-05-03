package dev.eshilov.mementomemobot.telegram.model

data class AnswerCallbackQueryRequest(
    val callbackQueryId: String,
    val text: String?
)
