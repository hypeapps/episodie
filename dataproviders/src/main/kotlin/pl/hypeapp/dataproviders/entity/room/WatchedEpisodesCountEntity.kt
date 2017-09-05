package pl.hypeapp.dataproviders.entity.room

import android.arch.persistence.room.ColumnInfo

data class WatchedEpisodesCountEntity(
        @ColumnInfo(name = "tv_show_id")
        var tvShowId: String = "",
        var count: Int = 0)
