package dev.eshilov.mementomemobot.process

import dev.eshilov.mementomemobot.remind.CheckAnswerOption
import dev.eshilov.mementomemobot.config.AppProps
import dev.eshilov.mementomemobot.meme.MemeEntity
import dev.eshilov.mementomemobot.meme.MemeRepository
import dev.eshilov.mementomemobot.telegram.operation.BotApi
import dev.eshilov.mementomemobot.telegram.model.AnswerCallbackQueryRequest
import dev.eshilov.mementomemobot.telegram.model.DeleteMessageRequest
import dev.eshilov.mementomemobot.telegram.model.SendMessageRequest
import dev.eshilov.mementomemobot.telegram.model.Update
import org.springframework.stereotype.Component

@Component
class CheckResponseUpdateProcessor(
    private val botApi: BotApi,
    private val appProps: AppProps,
    private val memeRepository: MemeRepository
) {
    fun processCheckResponse(update: Update) {
        if (isYesCheckResponse(update)) {
            handleYesCheckResponse(update)
        } else {
            handleNoCheckResponse(update)
        }

        deleteCheckMessage(update)
    }

    private fun isYesCheckResponse(update: Update): Boolean {
        val answer = update.callbackQuery?.data!!
        return answer == CheckAnswerOption.YES.key
    }

    private fun handleYesCheckResponse(update: Update) {
        sendYesAnswerCallbackQuery(update)
        val meme = findMemeForCheckResponseUpdate(update)
        meme?.let {
            markMemeAsChecked(meme)
            sendKeepMemeMessageToReceiver(meme)
            notifySenderThatMemeViewed(meme)
        }
    }

    private fun sendYesAnswerCallbackQuery(update: Update) {
        val callbackQueryId = getCallbackQueryId(update)
        botApi.answerCallbackQuery(
            AnswerCallbackQueryRequest(
                callbackQueryId = callbackQueryId,
                text = "Умничка!!!"
            )
        )
    }

    private fun findMemeForCheckResponseUpdate(update: Update): MemeEntity? {
        val checkMessageId = getCheckMessageId(update)
        return memeRepository.findOneByLastCheckMessageId(checkMessageId)
    }

    private fun markMemeAsChecked(meme: MemeEntity) {
        meme.viewed = true
        meme.lastCheckMessageId = null
        memeRepository.save(meme)
    }

    private fun notifySenderThatMemeViewed(meme: MemeEntity) {
        botApi.sendMessage(
            SendMessageRequest(
                chatId = appProps.senderChatId,
                text = "Котик посмотрел мем ${meme.content}",
                replyMarkup = null
            )
        )
    }

    private fun handleNoCheckResponse(update: Update) {
        botApi.answerCallbackQuery(
            AnswerCallbackQueryRequest(
                callbackQueryId = getCallbackQueryId(update),
                text = "Ну хорошо, только потом не забудь!!!"
            )
        )
    }

    private fun getCallbackQueryId(update: Update) = update.callbackQuery!!.id

    private fun getCheckMessageId(update: Update) = update.callbackQuery!!.message!!.messageId

    private fun deleteCheckMessage(update: Update) {
        botApi.deleteMessage(
            DeleteMessageRequest(
                chatId = appProps.receiverChatId,
                messageId = getCheckMessageId(update)
            )
        )
    }

    private fun sendKeepMemeMessageToReceiver(meme: MemeEntity) {
        botApi.sendMessage(
            SendMessageRequest(
                chatId = appProps.receiverChatId,
                text = "Сохраним на всякий случай ${meme.content}"
            )
        )
    }
}