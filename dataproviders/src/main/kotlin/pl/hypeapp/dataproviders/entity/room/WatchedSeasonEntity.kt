package pl.hypeapp.dataproviders.entity.room

import android.arch.persistence.room.*
import pl.hypeapp.domain.model.WatchState

@Entity(tableName = "watched_seasons",
        foreignKeys = [
            (ForeignKey(entity = WatchedTvShowEntity::class,
                    parentColumns = arrayOf("tv_show_id"),
                    childColumns = arrayOf("tv_show_id"),
                    onDelete = ForeignKey.CASCADE))
        ])
data class WatchedSeasonEntity(@PrimaryKey
                               @ColumnInfo(name = "season_id")
                               var seasonId: String = "",
                               @ColumnInfo(name = "tv_show_id")
                               var tvShowId: String = "",
                               @ColumnInfo(name = "watched_episodes_count")
                               var watchedEpisodesCount: Int = 0,
                               var runtime: Long = 0,
                               var watchState: String = WatchState.NOT_WATCHED,
                               @Ignore
                               var episodes: List<WatchedEpisodeEntity> = emptyList())
