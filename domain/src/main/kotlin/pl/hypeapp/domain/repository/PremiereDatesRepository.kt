package pl.hypeapp.domain.repository

import io.reactivex.Completable
import io.reactivex.Single
import pl.hypeapp.domain.model.PageableRequest
import pl.hypeapp.domain.model.premiere.PremiereDatesModel
import pl.hypeapp.domain.model.reminder.PremiereReminderModel
import java.util.*

interface PremiereDatesRepository {

    fun getPremiereDates(pageableRequest: PageableRequest, fromDate: Date, update: Boolean): Single<PremiereDatesModel>

    fun getPremiereReminderById(tvShowId: String): PremiereReminderModel

    fun getPremiereReminderSingleById(tvShowId: String): Single<PremiereReminderModel>

    fun addPremiereReminder(premiereReminderModel: PremiereReminderModel): Completable

    fun deletePremiereReminder(tvShowId: String): Completable

}
