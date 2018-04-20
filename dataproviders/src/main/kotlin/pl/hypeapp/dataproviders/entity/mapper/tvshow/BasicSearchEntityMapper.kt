package pl.hypeapp.dataproviders.entity.mapper.tvshow

import pl.hypeapp.dataproviders.entity.api.BasicSearchResultEntity
import pl.hypeapp.dataproviders.entity.mapper.Mapper
import pl.hypeapp.domain.model.tvshow.TvShowModel
import javax.inject.Inject

class BasicSearchEntityMapper @Inject constructor() : Mapper<TvShowModel, BasicSearchResultEntity>() {

    override fun transform(item: BasicSearchResultEntity?): TvShowModel? {
        return TvShowModel(
                id = item?.tvShowApiId,
                name = item?.name,
                episodeOrder = item?.episodeOrder,
                imageMedium = item?.imageMedium,
                imageOriginal = item?.imageOriginal,
                fullRuntime = item?.fullRuntime,
                episodeRuntime = null,
                genre = null,
                imdbId = null,
                network = null,
                officialSite = null,
                premiered = null,
                status = null,
                summary = null)
    }

}
