package pl.hypeapp.dataproviders.datasource

import io.reactivex.Single
import pl.hypeapp.dataproviders.entity.room.UserStatsEntity
import pl.hypeapp.dataproviders.service.room.RoomService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserStatsDataSource @Inject constructor(private val roomService: RoomService) : UserStatsDataStore {

    override fun getUserFullRuntime(): Single<Long> {
        return roomService.userStatsDao.getUserFullRuntime()
    }

    override fun getUserStats(): Single<UserStatsEntity> {
        return roomService.userStatsDao.getUserStats()
    }

}
