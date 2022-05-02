package dev.eshilov.mementomemobot.telegram.dto

data class Response<T>(
    val ok: Boolean,
    val result: T
)
