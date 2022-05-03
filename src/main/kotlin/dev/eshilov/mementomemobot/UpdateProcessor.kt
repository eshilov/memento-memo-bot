package dev.eshilov.mementomemobot

import dev.eshilov.mementomemobot.config.AppProps
import dev.eshilov.mementomemobot.meme.MemeEntity
import dev.eshilov.mementomemobot.meme.MemeRepository
import dev.eshilov.mementomemobot.telegram.TelegramBotApi
import dev.eshilov.mementomemobot.telegram.dto.AnswerCallbackQueryRequest
import dev.eshilov.mementomemobot.telegram.dto.DeleteMessageRequest
import dev.eshilov.mementomemobot.telegram.dto.SendMessageRequest
import dev.eshilov.mementomemobot.telegram.dto.Update
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.time.ZonedDateTime.now

@Component
class UpdateProcessor(
    private val appProps: AppProps,
    private val checkResponseUpdateProcessor: CheckResponseUpdateProcessor,
    private val newMemeRequestUpdateProcessor: NewMemeRequestUpdateProcessor
) {
    companion object {
        private val logger = LoggerFactory.getLogger(UpdateProcessor::class.java)
    }

    fun processUpdate(update: Update) {
        logger.info("Processing update ${update.updateId}: $update")
        if (isCheckResponse(update)) {
            checkResponseUpdateProcessor.processCheckResponse(update)
        } else if (isNewMemeRequest(update)) {
            newMemeRequestUpdateProcessor.processNewMemeRequest(update)
        }
    }

    private fun isCheckResponse(update: Update): Boolean {
        return update.callbackQuery?.message?.chat?.id == appProps.receiverChatId
    }

    private fun isNewMemeRequest(update: Update): Boolean {
        return update.message?.chat?.id == appProps.receiverChatId
    }
}