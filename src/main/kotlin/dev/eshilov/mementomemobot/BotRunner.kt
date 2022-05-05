package dev.eshilov.mementomemobot

import dev.eshilov.mementomemobot.fetch.UpdateFetcher
import dev.eshilov.mementomemobot.process.UpdateProcessingSubmitter
import dev.eshilov.mementomemobot.telegram.model.Update
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("!test")
class BotRunner(
    private val updateFetcher: UpdateFetcher,
    private val updateProcessingSubmitter: UpdateProcessingSubmitter
) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        var offset = 0L
        while (true) {
            val updates = updateFetcher.fetchUpdates(offset)
            submitUpdatesForProcessing(updates)
            offset = calculateNextOffset(offset, updates)
        }
    }

    private fun submitUpdatesForProcessing(updates: List<Update>) {
        updates.forEach(updateProcessingSubmitter::submitUpdateForProcessing)
    }

    private fun calculateNextOffset(currentOffset: Long, updates: List<Update>): Long {
        return updates.map { it.updateId }.maxOfOrNull { it }?.let { it + 1 } ?: currentOffset
    }
}