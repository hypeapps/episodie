package pl.hypeapp.dataproviders.repository

import io.reactivex.Completable
import io.reactivex.Single
import pl.hypeapp.dataproviders.datasource.DataFactory
import pl.hypeapp.dataproviders.entity.mapper.EpisodeReminderEntityMapper
import pl.hypeapp.dataproviders.entity.mapper.EpisodeReminderModelMapper
import pl.hypeapp.domain.model.EpisodeReminderModel
import pl.hypeapp.domain.repository.EpisodeReminderRepository
import javax.inject.Inject

class EpisodeReminderDataRepository @Inject constructor(private val dataFactory: DataFactory,
                                                        private val episodeReminderModelMapper: EpisodeReminderModelMapper,
                                                        private val episodeReminderEntityMapper: EpisodeReminderEntityMapper)
    : EpisodeReminderRepository {

    override fun getReminders(): Single<List<EpisodeReminderModel>> {
        return dataFactory.createEpisodeReminderDataSource()
                .getReminders()
                .map { episodeReminderModelMapper.transform(it) }
    }

    override fun insertReminders(episodeReminders: List<EpisodeReminderModel>): Completable {
        return Completable.fromAction {
            dataFactory.createEpisodeReminderDataSource()
                    .insertReminders(episodeReminderEntityMapper.transform(episodeReminders))
        }
    }

}
