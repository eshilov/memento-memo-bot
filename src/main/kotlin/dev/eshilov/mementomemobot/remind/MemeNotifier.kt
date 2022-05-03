package dev.eshilov.mementomemobot.remind

import dev.eshilov.mementomemobot.config.AppProps
import dev.eshilov.mementomemobot.meme.MemeEntity
import dev.eshilov.mementomemobot.meme.MemeRepository
import dev.eshilov.mementomemobot.telegram.model.InlineKeyboardButton
import dev.eshilov.mementomemobot.telegram.model.InlineKeyboardMarkup
import dev.eshilov.mementomemobot.telegram.model.Message
import dev.eshilov.mementomemobot.telegram.model.SendMessageRequest
import dev.eshilov.mementomemobot.telegram.operation.BotApi
import org.springframework.stereotype.Component
import java.time.ZonedDateTime

@Component
class MemeNotifier(
    private val botApi: BotApi,
    private val appProps: AppProps,
    private val memeRepository: MemeRepository
) {
    fun notifyAboutMeme(meme: MemeEntity, text: String) {
        val message = sendCheckMessage(meme, text)
        updateMemeLastCheck(meme, message)
    }

    private fun sendCheckMessage(meme: MemeEntity, text: String): Message {
        return botApi.sendMessage(
            SendMessageRequest(
                chatId = appProps.receiverChatId,
                text = "$text ${meme.content}",
                replyMarkup = buildCheckAnswerMarkup()
            )
        )
    }

    private fun buildCheckAnswerMarkup() = InlineKeyboardMarkup(
        inlineKeyboard = listOf(
            listOf(
                buildCheckAnswerOptionButton(CheckAnswerOption.YES),
                buildCheckAnswerOptionButton(CheckAnswerOption.NO)
            )
        )
    )

    private fun buildCheckAnswerOptionButton(option: CheckAnswerOption): InlineKeyboardButton = InlineKeyboardButton(
        text = option.label,
        callbackData = option.key
    )

    private fun updateMemeLastCheck(meme: MemeEntity, message: Message) {
        meme.lastChecked = ZonedDateTime.now()
        meme.lastCheckMessageId = message.messageId
        memeRepository.save(meme)
    }
}