package pl.hypeapp.dataproviders.entity.room

import android.arch.persistence.room.ColumnInfo

data class WatchedSeasonCountEntity(
        @ColumnInfo(name = "season_id")
        var seasonId: String = "",
        var count: Int = 0)
