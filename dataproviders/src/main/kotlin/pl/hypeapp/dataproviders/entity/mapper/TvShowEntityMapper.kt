package pl.hypeapp.dataproviders.entity.mapper

import pl.hypeapp.dataproviders.entity.TvShowEntity
import pl.hypeapp.domain.model.TvShowModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TvShowEntityMapper @Inject constructor() : EntityMapper<TvShowModel, TvShowEntity>() {

    override fun transform(entity: TvShowEntity?): TvShowModel =
            TvShowModel(
                    entity?.id,
                    entity?.imdbId,
                    entity?.name,
                    entity?.episodeRuntime,
                    entity?.fullRuntime,
                    entity?.premiered,
                    entity?.imageMedium,
                    entity?.imageOriginal)
}
