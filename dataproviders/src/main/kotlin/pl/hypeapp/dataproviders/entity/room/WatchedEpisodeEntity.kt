package pl.hypeapp.dataproviders.entity.room

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "watched_episodes")
data class WatchedEpisodeEntity(@PrimaryKey
                                @ColumnInfo(name = "episode_id")
                                var episodeId: String = "",
                                @ColumnInfo(name = "season_id")
                                var seasonId: String = "",
                                @ColumnInfo(name = "tv_show_id")
                                var tvShowId: String = "",
                                var runtime: Long = 0)
