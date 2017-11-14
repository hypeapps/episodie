package pl.hypeapp.dataproviders.entity.room

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "episode_reminders")
data class EpisodeReminderEntity(
        @PrimaryKey
        var episodeId: String = "",
        var tvShowId: String = "",
        var seasonId: String = "",
        var tvShowName: String = "",
        var name: String = "",
        var episodeNumber: Int = 0,
        var premiereDate: Long = 0L,
        var jobId: Int = 0)
