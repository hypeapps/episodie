package pl.hypeapp.dataproviders.entity.mapper.tvshow

import pl.hypeapp.dataproviders.entity.api.SeasonEntity
import pl.hypeapp.dataproviders.entity.mapper.Mapper
import pl.hypeapp.domain.model.tvshow.SeasonModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SeasonEntityMapper @Inject constructor(val episodeEntityMapper: EpisodeEntityMapper) : Mapper<SeasonModel, SeasonEntity>() {

    override fun transform(item: SeasonEntity?): SeasonModel? {
        return SeasonModel(
                seasonId = item?.seasonId,
                tvShowId = item?.tvShowId,
                episodeOrder = item?.episodes?.size,
                seasonNumber = item?.seasonNumber,
                fullRuntime = item?.runtime,
                premiereDate = item?.premiereDate,
                imageMedium = item?.imageMedium,
                episodes = episodeEntityMapper.transform(item!!.episodes))
    }

}
