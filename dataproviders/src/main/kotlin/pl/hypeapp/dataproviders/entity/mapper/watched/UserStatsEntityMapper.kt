package pl.hypeapp.dataproviders.entity.mapper.watched

import pl.hypeapp.dataproviders.entity.mapper.Mapper
import pl.hypeapp.dataproviders.entity.room.UserStatsEntity
import pl.hypeapp.domain.model.watched.UserStatsModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserStatsEntityMapper @Inject constructor() : Mapper<UserStatsModel, UserStatsEntity>() {

    override fun transform(item: UserStatsEntity?): UserStatsModel {
        return UserStatsModel(
                watchingTime = item?.watchingTime ?: 0,
                watchedEpisodesCount = item?.watchedEpisodesCount ?: 0,
                watchedSeasonsCount = item?.watchedSeasonsCount ?: 0,
                watchedTvShowsCount = item?.watchedTvShowsCount ?: 0
        )
    }
}
