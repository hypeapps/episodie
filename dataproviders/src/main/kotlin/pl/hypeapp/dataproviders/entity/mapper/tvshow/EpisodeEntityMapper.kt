package pl.hypeapp.dataproviders.entity.mapper.tvshow

import pl.hypeapp.dataproviders.entity.api.EpisodeEntity
import pl.hypeapp.dataproviders.entity.mapper.Mapper
import pl.hypeapp.domain.model.tvshow.EpisodeModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EpisodeEntityMapper @Inject constructor() : Mapper<EpisodeModel, EpisodeEntity>() {

    override fun transform(item: EpisodeEntity?): EpisodeModel? {
        return EpisodeModel(
                episodeId = item?.episodeId,
                tvShowId = item?.tvShowId,
                seasonId = item?.seasonId,
                name = item?.name,
                premiereDate = item?.premiereDate,
                runtime = item?.runtime,
                seasonNumber = item?.seasonNumber,
                episodeNumber = item?.episodeNumber,
                imageMedium = item?.imageMedium,
                summary = item?.summary)
    }

}
