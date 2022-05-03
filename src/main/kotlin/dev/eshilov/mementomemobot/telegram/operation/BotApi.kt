package dev.eshilov.mementomemobot.telegram.operation

import dev.eshilov.mementomemobot.telegram.model.AnswerCallbackQueryRequest
import dev.eshilov.mementomemobot.telegram.model.DeleteMessageRequest
import dev.eshilov.mementomemobot.telegram.model.Message
import dev.eshilov.mementomemobot.telegram.model.SendMessageRequest
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Component

@Component
class BotApi(
    private val botApiOperationPerformer: BotApiOperationPerformer
) {
    fun answerCallbackQuery(request: AnswerCallbackQueryRequest) {
        botApiOperationPerformer.performPostOperation(
            operation = BotApiOperations.ANSWER_CALLBACK_QUERY.key,
            body = request,
            resultType = object : ParameterizedTypeReference<Boolean>() {}
        )
    }

    fun sendMessage(request: SendMessageRequest): Message {
        return botApiOperationPerformer.performPostOperation(
            operation = BotApiOperations.SEND_MESSAGE.key,
            body = request,
            resultType = object : ParameterizedTypeReference<Message>() {}
        )
    }

    fun deleteMessage(request: DeleteMessageRequest): Boolean {
        return botApiOperationPerformer.performPostOperation(
            operation = BotApiOperations.DELETE_MESSAGE.key,
            body = request,
            resultType = object : ParameterizedTypeReference<Boolean>() {}
        )
    }
}