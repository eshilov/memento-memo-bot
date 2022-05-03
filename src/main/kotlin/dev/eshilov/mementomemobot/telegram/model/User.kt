package dev.eshilov.mementomemobot.telegram.model

data class User(
    val id: Long,
    val isBot: Boolean,
    val firstName: String,
    val lastName: String?,
    val username: String
)
