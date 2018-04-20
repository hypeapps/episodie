package pl.hypeapp.dataproviders.entity.mapper.seasontracker

import pl.hypeapp.dataproviders.entity.mapper.Mapper
import pl.hypeapp.dataproviders.entity.room.SeasonTrackerEntity
import pl.hypeapp.domain.model.collections.SeasonTrackerModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SeasonTrackerEntityMapper @Inject constructor() : Mapper<SeasonTrackerEntity, SeasonTrackerModel>() {

    override fun transform(item: SeasonTrackerModel?): SeasonTrackerEntity? {
        return SeasonTrackerEntity(
                tvShowId = item?.tvShowId!!,
                seasonId = item.seasonId,
                watchedEpisodes = item.watchedEpisodes)
    }

}
