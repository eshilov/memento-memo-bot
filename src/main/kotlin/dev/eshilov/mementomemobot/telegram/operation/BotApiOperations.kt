package dev.eshilov.mementomemobot.telegram.operation

enum class BotApiOperations(
    val key: String
) {
    ANSWER_CALLBACK_QUERY(key = "answerCallbackQuery"),
    SEND_MESSAGE(key = "sendMessage"),
    DELETE_MESSAGE(key = "deleteMessage")
}