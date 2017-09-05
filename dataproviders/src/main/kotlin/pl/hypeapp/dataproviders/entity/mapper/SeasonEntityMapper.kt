package pl.hypeapp.dataproviders.entity.mapper

import pl.hypeapp.dataproviders.entity.SeasonEntity
import pl.hypeapp.domain.model.SeasonModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SeasonEntityMapper @Inject constructor(val episodeEntityMapper: EpisodeEntityMapper) : EntityMapper<SeasonModel, SeasonEntity>() {

    override fun transform(entity: SeasonEntity?): SeasonModel? {
        return SeasonModel(
                seasonId = entity?.seasonId,
                tvShowId = entity?.tvShowId,
                episodeOrder = entity?.episodes?.size,
                seasonNumber = entity?.seasonNumber,
                runtime = entity?.runtime,
                premiereDate = entity?.premiereDate,
                imageMedium = entity?.imageMedium,
                episodes = episodeEntityMapper.transform(entity!!.episodes))
    }

}
