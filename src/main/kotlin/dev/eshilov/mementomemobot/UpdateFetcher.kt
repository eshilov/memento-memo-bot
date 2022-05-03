package dev.eshilov.mementomemobot

import dev.eshilov.mementomemobot.config.AppProps
import dev.eshilov.mementomemobot.telegram.action.OperationPerformer
import dev.eshilov.mementomemobot.telegram.dto.Update
import org.slf4j.LoggerFactory
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Component

@Component
class UpdateFetcher(
    private val appProps: AppProps,
    private val operationPerformer: OperationPerformer
) {
    companion object {
        private val logger = LoggerFactory.getLogger(UpdateFetcher::class.java)
    }

    fun fetchUpdates(offset: Long): List<Update> {
        logger.info(
            "Fetching updates with offset $offset" +
                    " (batchSize: ${appProps.batchSize}," +
                    " timeout: ${appProps.timeoutSeconds} secs)"
        )

        val operation = buildGetUpdatesOperation(offset)
        val updates = operationPerformer.performGetOperation(
            operation,
            object : ParameterizedTypeReference<List<Update>>() {}
        )

        logger.info("Fetched ${updates.size} updates: ${updates.map { it.updateId }}")

        return updates
    }


    private fun buildGetUpdatesOperation(offset: Long): String {
        val (batchSize, timeoutSeconds) = appProps
        return "getUpdates?offset=$offset&limit=$batchSize&timeout=$timeoutSeconds"
    }
}