package pl.hypeapp.dataproviders.entity.mapper.tvshow

import pl.hypeapp.dataproviders.entity.api.TvShowEntity
import pl.hypeapp.dataproviders.entity.mapper.Mapper
import pl.hypeapp.domain.model.tvshow.TvShowModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TvShowEntityMapper @Inject constructor() : Mapper<TvShowModel, TvShowEntity>() {

    override fun transform(item: TvShowEntity?): TvShowModel =
            TvShowModel(
                    id = item?.id,
                    imdbId = item?.imdbId,
                    name = item?.name,
                    episodeRuntime = item?.episodeRuntime,
                    fullRuntime = item?.fullRuntime,
                    episodeOrder = item?.episodeOrder,
                    premiered = item?.premiered,
                    summary = item?.summary,
                    imageMedium = item?.imageMedium,
                    status = item?.status,
                    imageOriginal = item?.imageOriginal,
                    genre = item?.genre,
                    officialSite = item?.officialSite,
                    network = item?.network)

}
