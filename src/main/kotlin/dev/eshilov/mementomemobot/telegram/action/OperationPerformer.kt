package dev.eshilov.mementomemobot.telegram.action

import dev.eshilov.mementomemobot.config.AppProps
import dev.eshilov.mementomemobot.telegram.dto.Response
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

@Component
class OperationPerformer(
    private val appProps: AppProps,
    private val restTemplate: RestTemplate
) {
    fun <T> performGetOperation(operation: String, resultType: ParameterizedTypeReference<T>): T {
        val request = buildGetOperationRequest(operation)
        val responseType = buildWrappedResponseType(resultType)
        val response = restTemplate.exchange(request, responseType)
        return extractResultFromResponse(response)
    }

    private fun buildGetOperationRequest(operation: String): RequestEntity<*> {
        val url = buildUrlForOperation(operation)
        return RequestEntity.get(url).build()
    }

    private fun buildUrlForOperation(action: String): String {
        return "https://api.telegram.org/bot${appProps.botToken}/$action"
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
                body.result
            } else {
                throw Exception("Response is not ok")
            }
        } ?: throw Exception("Response body is null")
    }
}