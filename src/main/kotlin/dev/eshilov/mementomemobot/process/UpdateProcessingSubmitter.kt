package dev.eshilov.mementomemobot.process

import dev.eshilov.mementomemobot.config.AppProps
import dev.eshilov.mementomemobot.telegram.model.Update
import org.slf4j.LoggerFactory
import org.springframework.core.task.TaskExecutor
import org.springframework.stereotype.Component

@Component
class UpdateProcessingSubmitter(
    private val appProps: AppProps,
    private val processingTaskExecutor: TaskExecutor,
    private val updateProcessor: UpdateProcessor
) {
    companion object {
        private val logger = LoggerFactory.getLogger(UpdateProcessingSubmitter::class.java)
    }

    fun submitUpdateForProcessing(update: Update) {
        var attemptCount = 0
        var submitted = false
        while (attemptCount < appProps.maxSubmitAttempts && !submitted) {
            attemptCount++
            try {
                logger.info("Submitting update ${update.updateId} for processing ($attemptCount attempt)")
                processingTaskExecutor.execute {
                    processUpdate(update)
                }
                submitted = true
            } catch (ex: Exception) {
                logger.warn(
                    "Could not submit update ${update.updateId}" +
                            " for processing: ${ex.message}"
                )
            }
        }

        if (!submitted) {
            throw Exception(
                "Could not submit update ${update.updateId}" +
                        " for processing after $attemptCount attempts"
            )
        }
    }

    private fun processUpdate(update: Update) {
        try {
            updateProcessor.processUpdate(update)
        } catch (ex: Exception) {
            logger.error("Error occurred while processing update ${update.updateId}", ex)
        }
    }
}