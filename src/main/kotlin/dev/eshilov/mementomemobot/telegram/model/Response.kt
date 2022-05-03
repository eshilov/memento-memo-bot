package dev.eshilov.mementomemobot.telegram.model

data class Response<T>(
    val ok: Boolean,
    val result: T
)
