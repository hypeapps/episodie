package pl.hypeapp.dataproviders.datasource

import io.reactivex.Single
import pl.hypeapp.dataproviders.entity.room.EpisodeReminderEntity
import pl.hypeapp.dataproviders.service.room.RoomService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EpisodeReminderDataSource @Inject constructor(private val roomService: RoomService)
    : EpisodeReminderDataStore {

    override fun getReminders(): Single<List<EpisodeReminderEntity>> {
        return roomService.episodeReminderDao.getReminders()
    }

    override fun insertReminders(episodeReminders: List<EpisodeReminderEntity>) = executeQuery {
        roomService.episodeReminderDao.deleteAll()
        roomService.episodeReminderDao.insertReminders(episodeReminders)
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
