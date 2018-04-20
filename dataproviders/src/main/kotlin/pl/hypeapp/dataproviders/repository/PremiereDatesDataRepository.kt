package pl.hypeapp.dataproviders.repository

import io.reactivex.Completable
import io.reactivex.Single
import pl.hypeapp.dataproviders.datasource.DataFactory
import pl.hypeapp.dataproviders.entity.mapper.reminder.PremiereReminderEntityMapper
import pl.hypeapp.dataproviders.entity.mapper.reminder.PremiereReminderModelMapper
import pl.hypeapp.dataproviders.entity.mapper.tvshow.PremiereDatesEntityMapper
import pl.hypeapp.domain.model.PageableRequest
import pl.hypeapp.domain.model.premiere.PremiereDatesModel
import pl.hypeapp.domain.model.reminder.PremiereReminderModel
import pl.hypeapp.domain.repository.PremiereDatesRepository
import java.util.*
import javax.inject.Inject

class PremiereDatesDataRepository @Inject constructor(val dataFactory: DataFactory,
                                                      private val premiereDatesEntityMapper: PremiereDatesEntityMapper,
                                                      private val premiereReminderEntityMapper: PremiereReminderEntityMapper,
                                                      private val premiereReminderModelMapper: PremiereReminderModelMapper)
    : PremiereDatesRepository {

    override fun getPremiereDates(pageableRequest: PageableRequest, fromDate: Date, update: Boolean): Single<PremiereDatesModel> {
        return dataFactory.createTvShowDataSource()
                .getPremiereDates(pageableRequest, fromDate, update)
                .map { premiereDatesEntityMapper.transform(it) }
    }

    override fun getPremiereReminderById(tvShowId: String): PremiereReminderModel {
        return dataFactory.createPremiereReminderDataSource()
                .getReminderById(tvShowId).let { premiereReminderEntityMapper.transform(it) }
    }

    override fun getPremiereReminderSingleById(tvShowId: String): Single<PremiereReminderModel> {
        return dataFactory.createPremiereReminderDataSource()
                .getReminderSingleById(tvShowId)
                .map { premiereReminderEntityMapper.transform(it) }
    }

    override fun addPremiereReminder(premiereReminderModel: PremiereReminderModel): Completable {
        return Completable.fromAction {
            dataFactory.createPremiereReminderDataSource()
                    .insertReminder(premiereReminderModelMapper.transform(premiereReminderModel))
        }
    }

    override fun deletePremiereReminder(tvShowId: String): Completable {
        return Completable.fromAction {
            dataFactory.createPremiereReminderDataSource()
                    .deleteReminder(tvShowId)
        }
    }
}
