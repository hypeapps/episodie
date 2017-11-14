package pl.hypeapp.dataproviders.entity.mapper

import pl.hypeapp.dataproviders.entity.api.AllSeasonsEntity
import pl.hypeapp.domain.model.TvShowExtendedModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AllSeasonsEntityMapper @Inject constructor(private val seasonEntityMapper: SeasonEntityMapper)
    : EntityMapper<TvShowExtendedModel, AllSeasonsEntity>() {

    override fun transform(entity: AllSeasonsEntity?): TvShowExtendedModel {
        return TvShowExtendedModel(
                tvShowId = entity?.tvShowId,
                name = entity?.name,
                premiered = entity?.premiered,
                status = entity?.status,
                fullRuntime = entity?.fullRuntime,
                imageMedium = entity?.imageMedium,
                imageOriginal = entity?.imageOriginal,
                seasons = seasonEntityMapper.transform(entity?.seasons!!))
    }

}
