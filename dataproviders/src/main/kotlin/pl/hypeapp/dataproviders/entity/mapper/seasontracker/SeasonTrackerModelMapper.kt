package pl.hypeapp.dataproviders.entity.mapper.seasontracker

import pl.hypeapp.dataproviders.entity.mapper.Mapper
import pl.hypeapp.dataproviders.entity.room.SeasonTrackerEntity
import pl.hypeapp.domain.model.collections.SeasonTrackerModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SeasonTrackerModelMapper @Inject constructor() : Mapper<SeasonTrackerModel, SeasonTrackerEntity>() {

    override fun transform(item: SeasonTrackerEntity?): SeasonTrackerModel? {
        return SeasonTrackerModel(
                item?.tvShowId!!,
                item.seasonId,
                item.watchedEpisodes
        )
    }

}

