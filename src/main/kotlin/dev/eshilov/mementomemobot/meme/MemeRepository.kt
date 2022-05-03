package dev.eshilov.mementomemobot.meme;

import org.springframework.data.jpa.repository.JpaRepository
import java.time.ZonedDateTime

interface MemeRepository : JpaRepository<MemeEntity, Long> {

    fun findFirstByViewedIsFalseAndLastCheckedIsBeforeOrderByLastChecked(
        lastCheckedBoundary: ZonedDateTime
    ): MemeEntity?

    fun findOneByLastCheckMessageId(lastCheckMessageId: Long): MemeEntity?
}