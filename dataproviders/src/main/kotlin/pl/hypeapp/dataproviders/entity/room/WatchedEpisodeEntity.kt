package pl.hypeapp.dataproviders.entity.room

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import pl.hypeapp.domain.model.WatchState

@Entity(tableName = "watched_episodes",
        foreignKeys = [
            (ForeignKey(entity = WatchedSeasonEntity::class,
                    parentColumns = arrayOf("season_id"),
                    childColumns = arrayOf("season_id"),
                    onDelete = ForeignKey.CASCADE))
        ])
data class WatchedEpisodeEntity(@PrimaryKey
                                @ColumnInfo(name = "episode_id")
                                var episodeId: String = "",
                                @ColumnInfo(name = "season_id")
                                var seasonId: String = "",
                                @ColumnInfo(name = "tv_show_id")
                                var tvShowId: String = "",
                                var runtime: Long = 0,
                                var watchState: String = WatchState.NOT_WATCHED)
