package pl.hypeapp.domain.repository

import io.reactivex.Completable
import io.reactivex.Single
import pl.hypeapp.domain.model.reminder.EpisodeReminderModel

interface EpisodeReminderRepository {

    fun getReminders(): Single<List<EpisodeReminderModel>>

    fun insertReminders(episodeReminders: List<EpisodeReminderModel>): Completable

}
