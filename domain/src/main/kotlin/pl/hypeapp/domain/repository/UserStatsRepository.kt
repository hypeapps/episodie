package pl.hypeapp.domain.repository

import io.reactivex.Single
import pl.hypeapp.domain.model.watched.UserStatsModel

interface UserStatsRepository {

    fun getUserFullRuntime(): Single<Long>

    fun getUserStats(): Single<UserStatsModel>

}
