package dev.eshilov.mementomemobot.dto

data class Response<T>(
    val ok: Boolean,
    val result: T
)
