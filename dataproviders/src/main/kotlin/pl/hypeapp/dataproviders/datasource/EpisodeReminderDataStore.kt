package pl.hypeapp.dataproviders.datasource

import io.reactivex.Single
import pl.hypeapp.dataproviders.entity.room.EpisodeReminderEntity

interface EpisodeReminderDataStore {

    fun getReminders(): Single<List<EpisodeReminderEntity>>

    fun insertReminders(episodeReminders: List<EpisodeReminderEntity>)

}
