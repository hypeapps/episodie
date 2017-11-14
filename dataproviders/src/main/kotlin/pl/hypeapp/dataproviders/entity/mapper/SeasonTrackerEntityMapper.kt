package pl.hypeapp.dataproviders.entity.mapper

import pl.hypeapp.dataproviders.entity.room.SeasonTrackerEntity
import pl.hypeapp.domain.model.SeasonTrackerModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SeasonTrackerEntityMapper @Inject constructor() : EntityMapper<SeasonTrackerEntity, SeasonTrackerModel>() {

    override fun transform(entity: SeasonTrackerModel?): SeasonTrackerEntity? {
        return SeasonTrackerEntity(
                tvShowId = entity?.tvShowId!!,
                seasonId = entity.seasonId,
                watchedEpisodes = entity.watchedEpisodes)
    }

}
