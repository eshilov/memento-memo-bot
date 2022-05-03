package dev.eshilov.mementomemobot.remind

import dev.eshilov.mementomemobot.config.AppProps
import dev.eshilov.mementomemobot.meme.MemeEntity
import dev.eshilov.mementomemobot.meme.MemeRepository
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.ZonedDateTime.now

@Component
class RemindScheduler(
    private val memeRepository: MemeRepository,
    private val appProps: AppProps,
    private val memeNotifier: MemeNotifier
) {
    companion object {
        private val logger = LoggerFactory.getLogger(RemindScheduler::class.java)
    }

    @Scheduled(cron = "0 0/30 * * * *")
    fun scheduledCheck() {
        logger.info("Processing scheduled reminder")

        val meme = findMemeToCheck()
        meme?.let {
            logger.info("Reminding about meme ${meme.id}")

            memeNotifier.notifyAboutMeme(meme, "Уже посмотрел мем от кошечки?")
        }
            ?: logger.info("Meme to remind not found")
    }

    private fun findMemeToCheck(): MemeEntity? {
        val lastCheckedBoundary = now().minusMinutes(appProps.minRemindFrequencyMinutes)
        return memeRepository.findFirstByViewedIsFalseAndLastCheckedIsBeforeOrderByLastChecked(lastCheckedBoundary)
    }
}