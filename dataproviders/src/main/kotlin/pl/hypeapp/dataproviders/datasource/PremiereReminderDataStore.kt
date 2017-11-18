package pl.hypeapp.dataproviders.datasource

import io.reactivex.Single
import pl.hypeapp.dataproviders.entity.room.PremiereReminderEntity

interface PremiereReminderDataStore {

    fun insertReminder(premiereReminderEntity: PremiereReminderEntity)

    fun getReminderById(tvShowId: String): PremiereReminderEntity

    fun getReminderSingleById(tvShowId: String): Single<PremiereReminderEntity>

    fun deleteReminder(tvShowId: String)

}
