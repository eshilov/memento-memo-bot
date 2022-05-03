package dev.eshilov.mementomemobot.telegram.operation

import dev.eshilov.mementomemobot.config.AppProps
import dev.eshilov.mementomemobot.telegram.model.Response
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

@Component
class BotApiOperationPerformer(
    private val appProps: AppProps,
    private val restTemplate: RestTemplate
) {
    fun <T> performGetOperation(operation: String, resultType: ParameterizedTypeReference<T>): T {
        val request = buildGetOperationRequest(operation)
        return sendRequest(request, resultType)
    }

    fun <T> performPostOperation(operation: String, body: Any?, resultType: ParameterizedTypeReference<T>): T {
        val request = buildPostOperationRequest(operation, body)
        return sendRequest(request, resultType)
    }

    private fun buildGetOperationRequest(operation: String): RequestEntity<*> {
        val url = buildUrlForOperation(operation)
        return RequestEntity.get(url).build()
    }

    private fun buildPostOperationRequest(operation: String, body: Any?): RequestEntity<*> {
        val url = buildUrlForOperation(operation)
        val builder = RequestEntity.post(url)
        return body?.let {
            builder.header(CONTENT_TYPE, APPLICATION_JSON_VALUE).body(it)
        } ?: builder.build()
    }

    private fun buildUrlForOperation(action: String): String {
        return "https://api.telegram.org/bot${appProps.botToken}/$action"
    }

    private fun <T> sendRequest(request: RequestEntity<*>, resultType: ParameterizedTypeReference<T>): T {
        val responseType = buildWrappedResponseType(resultType)
        val response = restTemplate.exchange(request, responseType)
        return extractResultFromResponse(response)
    }

    private fun <T> buildWrappedResponseType(
        resultType: ParameterizedTypeReference<T>
    ): ParameterizedTypeReference<Response<T>> {
        val type = object : ParameterizedType {
            override fun getActualTypeArguments(): Array<Type> {
                return arrayOf(resultType.type)
            }

            override fun getRawType(): Type {
                return Response::class.java
            }

            override fun getOwnerType(): Type {
                return null!!
            }

        }
        return ParameterizedTypeReference.forType(type)
    }

    private fun <T> extractResultFromResponse(response: ResponseEntity<Response<T>>): T {
        return response.body?.let { body ->
            if (body.ok) {
                println(body.result)
                body.result
            } else {
                throw Exception("Response is not ok")
            }
        } ?: throw Exception("Response body is null")
    }
}