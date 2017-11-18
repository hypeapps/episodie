package pl.hypeapp.dataproviders.datasource

import io.reactivex.Single
import pl.hypeapp.dataproviders.entity.room.PremiereReminderEntity
import pl.hypeapp.dataproviders.service.room.RoomService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PremiereReminderDataSource @Inject constructor(private val roomService: RoomService) : PremiereReminderDataStore {

    override fun insertReminder(premiereReminderEntity: PremiereReminderEntity) = executeQuery {
        roomService.premiereReminderDao.insertReminder(premiereReminderEntity)
    }

    override fun getReminderById(tvShowId: String): PremiereReminderEntity {
        return roomService.premiereReminderDao.getReminderById(tvShowId)
    }

    override fun deleteReminder(tvShowId: String) = executeQuery {
        roomService.premiereReminderDao.deleteReminder(tvShowId)
    }

    override fun getReminderSingleById(tvShowId: String): Single<PremiereReminderEntity> {
        return roomService.premiereReminderDao.getReminderSingleById(tvShowId)
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
