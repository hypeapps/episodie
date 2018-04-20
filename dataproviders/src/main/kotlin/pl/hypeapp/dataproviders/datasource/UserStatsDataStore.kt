package pl.hypeapp.dataproviders.datasource

import io.reactivex.Flowable
import io.reactivex.Single
import pl.hypeapp.dataproviders.entity.room.UserStatsEntity

interface UserStatsDataStore {

    fun getUserFullRuntime(): Single<Long>

    fun getUserFullRuntimeFlowable(): Flowable<Long>

    fun getUserStats(): Single<UserStatsEntity>
}
