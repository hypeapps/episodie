package pl.hypeapp.dataproviders.entity.room

import android.arch.persistence.room.ColumnInfo

data class UserStatsEntity(@ColumnInfo(name = "runtime")
                           var watchingTime: Long,
                           @ColumnInfo(name = "watched_tv_shows")
                           var watchedTvShowsCount: Int,
                           @ColumnInfo(name = "watched_episodes")
                           var watchedEpisodesCount: Int,
                           @ColumnInfo(name = "watched_seasons")
                           var watchedSeasonsCount: Int)
