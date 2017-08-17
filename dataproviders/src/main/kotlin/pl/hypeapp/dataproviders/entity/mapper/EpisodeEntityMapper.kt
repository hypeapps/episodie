package pl.hypeapp.dataproviders.entity.mapper

import pl.hypeapp.dataproviders.entity.EpisodeEntity
import pl.hypeapp.domain.model.EpisodeModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EpisodeEntityMapper @Inject constructor() : EntityMapper<EpisodeModel, EpisodeEntity>() {

    override fun transform(entity: EpisodeEntity?): EpisodeModel? {
        return EpisodeModel(
                episodeId = entity?.episodeId,
                name = entity?.name,
                premiereDate = entity?.premiereDate,
                runtime = entity?.runtime,
                seasonNumber = entity?.seasonNumber,
                episodeNumber = entity?.episodeNumber,
                imageMedium = entity?.imageMedium,
                summary = entity?.summary)
    }

}
