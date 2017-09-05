package pl.hypeapp.dataproviders.entity.mapper

import pl.hypeapp.dataproviders.entity.TvShowEntity
import pl.hypeapp.domain.model.TvShowModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TvShowEntityMapper @Inject constructor() : EntityMapper<TvShowModel, TvShowEntity>() {

    override fun transform(entity: TvShowEntity?): TvShowModel =
            TvShowModel(
                    id = entity?.id,
                    imdbId = entity?.imdbId,
                    name = entity?.name,
                    episodeRuntime = entity?.episodeRuntime,
                    fullRuntime = entity?.fullRuntime,
                    episodeOrder = entity?.episodeOrder,
                    premiered = entity?.premiered,
                    summary = entity?.summary,
                    imageMedium = entity?.imageMedium,
                    status = entity?.status,
                    imageOriginal = entity?.imageOriginal,
                    genre = entity?.genre,
                    officialSite = entity?.officialSite,
                    network = entity?.network)

}
