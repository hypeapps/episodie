package pl.hypeapp.dataproviders.service.room.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import io.reactivex.Single
import pl.hypeapp.dataproviders.entity.room.UserStatsEntity

@Dao
interface UserStatsDao {

    @Query("SELECT sum(runtime) FROM watched_tv_shows")
    fun getUserFullRuntime(): Single<Long>

    @Query("SELECT sum(runtime) as runtime, sum(watched_episodes_count) as watched_episodes, " +
            "sum(watched_seasons_count) as watched_seasons, count(*) as watched_tv_shows FROM watched_tv_shows")
    fun getUserStats(): Single<UserStatsEntity>

}
