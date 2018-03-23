package pl.hypeapp.dataproviders.repository

import io.reactivex.Completable
import io.reactivex.Single
import pl.hypeapp.dataproviders.datasource.DataFactory
import pl.hypeapp.dataproviders.entity.mapper.seasontracker.SeasonTrackerEntityMapper
import pl.hypeapp.dataproviders.entity.mapper.seasontracker.SeasonTrackerModelMapper
import pl.hypeapp.domain.model.collections.SeasonTrackerModel
import pl.hypeapp.domain.repository.SeasonTrackerRepository
import javax.inject.Inject

class SeasonTrackerDataRepository @Inject constructor(val dataFactory: DataFactory,
                                                      private val seasonTrackerEntityMapper: SeasonTrackerEntityMapper,
                                                      private val seasonTrackerModelMapper: SeasonTrackerModelMapper)
    : SeasonTrackerRepository {

    override fun addSeasonToSeasonTracker(seasonTrackerModel: SeasonTrackerModel): Completable {
        return Completable.fromAction {
            dataFactory.createSeasonTrackerDataSource()
                    .insertSeasonToSeasonTracker(seasonTrackerEntityMapper.transform(seasonTrackerModel)!!)
        }
    }

    override fun getSeasonTackerModel(): Single<SeasonTrackerModel> {
        return dataFactory.createSeasonTrackerDataSource()
                .getSeasonTracker()
                .map { seasonTrackerModelMapper.transform(it)!! }
    }

    override fun deleteSeasonTracker(): Completable {
        return Completable.fromAction {
            dataFactory.createSeasonTrackerDataSource()
                    .deleteSeasonTracker()
        }
    }
}
