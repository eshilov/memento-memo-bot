package dev.eshilov.mementomemobot.process

import dev.eshilov.mementomemobot.meme.MemeEntity
import dev.eshilov.mementomemobot.meme.MemeRepository
import dev.eshilov.mementomemobot.telegram.model.Update
import org.springframework.stereotype.Component
import java.time.ZonedDateTime

@Component
class NewMemeRequestUpdateProcessor(
    private val memeRepository: MemeRepository
) {

    fun processNewMemeRequest(update: Update) {
        val meme = buildNewMemeForUpdate(update)
        memeRepository.save(meme)
    }

    private fun buildNewMemeForUpdate(update: Update): MemeEntity {
        return MemeEntity(
            id = 0,
            content = update.message?.text?.trim() ?: "",
            viewed = false,
            lastChecked = ZonedDateTime.now()
        )
    }
}