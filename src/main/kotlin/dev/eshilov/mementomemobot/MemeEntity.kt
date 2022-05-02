package dev.eshilov.mementomemobot

import java.time.ZonedDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "memes")
data class MemeEntity(

    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Long,
    val content: String,
    val viewed: Boolean,
    val lastChecked: ZonedDateTime
)
