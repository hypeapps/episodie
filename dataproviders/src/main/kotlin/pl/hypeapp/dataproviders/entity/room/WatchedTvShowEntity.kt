package pl.hypeapp.dataproviders.entity.room

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import pl.hypeapp.domain.model.WatchState

@Entity(tableName = "watched_tv_shows")
data class WatchedTvShowEntity(@PrimaryKey
                               @ColumnInfo(name = "tv_show_id")
                               var tvShowId: String = "",
                               var title: String = "",
                               @ColumnInfo(name = "watched_episodes_count")
                               var watchedEpisodesCount: Int = 0,
                               @ColumnInfo(name = "watched_seasons_count")
                               var watchedSeasonsCount: Int = 0,
                               var runtime: Long = 0,
                               @ColumnInfo(name = "watch_state")
                               var watchState: String = WatchState.NOT_WATCHED,
                               @Ignore
                               var seasons: List<WatchedSeasonEntity> = emptyList())
