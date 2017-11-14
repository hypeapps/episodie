package pl.hypeapp.dataproviders.datasource

import io.reactivex.Single
import pl.hypeapp.dataproviders.entity.room.SeasonTrackerEntity
import pl.hypeapp.dataproviders.service.room.RoomService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SeasonTrackerDataSource @Inject constructor(private val roomService: RoomService) : SeasonTrackerDataStore {

    override fun insertSeasonToSeasonTracker(seasonTrackerEntity: SeasonTrackerEntity) = executeQuery {
        roomService.seasonTrackerDao.clearAll()
        roomService.seasonTrackerDao.insertSeasonToSeasonTracker(seasonTrackerEntity)
    }

    override fun getSeasonTracker(): Single<SeasonTrackerEntity> {
        return roomService.seasonTrackerDao.getSeasonTracker()
    }

    override fun deleteSeasonTracker() = executeQuery {
        roomService.seasonTrackerDao.clearAll()
    }

    private inline fun executeQuery(query: () -> Unit) {
        roomService.beginTransaction()
        try {
            query()
            roomService.setTransactionSuccessful()
        } finally {
            roomService.endTransaction()
        }
    }

}
