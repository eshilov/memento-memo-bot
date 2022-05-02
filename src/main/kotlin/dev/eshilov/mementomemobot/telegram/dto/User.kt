package dev.eshilov.mementomemobot.telegram.dto

data class User(
    val id: Long,
    val isBot: Boolean,
    val firstName: String,
    val lastName: String?,
    val username: String
)