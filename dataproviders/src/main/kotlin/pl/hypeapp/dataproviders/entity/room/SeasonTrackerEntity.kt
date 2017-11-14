package pl.hypeapp.dataproviders.entity.room

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "season_tracker")
data class SeasonTrackerEntity(@PrimaryKey
                               @ColumnInfo(name = "tv_show_id")
                               var tvShowId: String = "",
                               var seasonId: String = "",
                               var watchedEpisodes: ArrayList<String> = arrayListOf())
