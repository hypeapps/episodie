package pl.hypeapp.dataproviders.datasource

import io.reactivex.Single
import pl.hypeapp.dataproviders.entity.room.UserStatsEntity

interface UserStatsDataStore {

    fun getUserFullRuntime(): Single<Long>

    fun getUserStats(): Single<UserStatsEntity>
}
