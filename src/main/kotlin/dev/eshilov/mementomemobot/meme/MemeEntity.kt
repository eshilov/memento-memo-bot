package dev.eshilov.mementomemobot.meme

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
    var id: Long,
    var content: String,
    var viewed: Boolean,
    var lastChecked: ZonedDateTime,
    var lastCheckMessageId: Long? = null
)
