package pl.hypeapp.dataproviders.entity.mapper.tvshow

import pl.hypeapp.dataproviders.entity.api.AllSeasonsEntity
import pl.hypeapp.dataproviders.entity.mapper.Mapper
import pl.hypeapp.domain.model.tvshow.TvShowExtendedModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AllSeasonsEntityMapper @Inject constructor(private val seasonEntityMapper: SeasonEntityMapper)
    : Mapper<TvShowExtendedModel, AllSeasonsEntity>() {

    override fun transform(item: AllSeasonsEntity?): TvShowExtendedModel {
        return TvShowExtendedModel(
                tvShowId = item?.tvShowId,
                name = item?.name,
                premiered = item?.premiered,
                status = item?.status,
                fullRuntime = item?.fullRuntime,
                imageMedium = item?.imageMedium,
                imageOriginal = item?.imageOriginal,
                seasons = seasonEntityMapper.transform(item?.seasons!!))
    }

}
