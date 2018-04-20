package pl.hypeapp.dataproviders.repository

import io.reactivex.Flowable
import io.reactivex.Single
import pl.hypeapp.dataproviders.datasource.DataFactory
import pl.hypeapp.dataproviders.entity.mapper.watched.UserStatsEntityMapper
import pl.hypeapp.domain.model.watched.UserStatsModel
import pl.hypeapp.domain.repository.UserStatsRepository
import javax.inject.Inject

class UserStatsDataRepository @Inject constructor(private val dataFactory: DataFactory,
                                                  private val userStatsEntityMapper: UserStatsEntityMapper)
    : UserStatsRepository {

    override fun getUserFullRuntime(): Single<Long> {
        return dataFactory
                .createUserStatsDataSource()
                .getUserFullRuntime()
    }

    override fun getUserFullRuntimeFlowable(): Flowable<Long> {
        return dataFactory
                .createUserStatsDataSource()
                .getUserFullRuntimeFlowable()
    }

    override fun getUserStats(): Single<UserStatsModel> {
        return dataFactory
                .createUserStatsDataSource()
                .getUserStats()
                .map { userStatsEntityMapper.transform(it) }
    }
}
