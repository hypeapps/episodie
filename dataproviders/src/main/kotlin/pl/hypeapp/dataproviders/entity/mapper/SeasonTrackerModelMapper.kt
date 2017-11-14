package pl.hypeapp.dataproviders.entity.mapper

import pl.hypeapp.dataproviders.entity.room.SeasonTrackerEntity
import pl.hypeapp.domain.model.SeasonTrackerModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SeasonTrackerModelMapper @Inject constructor() : EntityMapper<SeasonTrackerModel, SeasonTrackerEntity>() {

    override fun transform(entity: SeasonTrackerEntity?): SeasonTrackerModel? {
        return SeasonTrackerModel(
                entity?.tvShowId!!,
                entity.seasonId,
                entity.watchedEpisodes
        )
    }

}

