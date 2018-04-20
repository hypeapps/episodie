package pl.hypeapp.dataproviders.entity.mapper.tvshow

import pl.hypeapp.dataproviders.entity.api.PageablePremiereDates
import pl.hypeapp.dataproviders.entity.mapper.Mapper
import pl.hypeapp.domain.model.PageableRequest
import pl.hypeapp.domain.model.premiere.PremiereDateModel
import pl.hypeapp.domain.model.premiere.PremiereDatesModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PremiereDatesEntityMapper @Inject constructor() : Mapper<PremiereDatesModel, PageablePremiereDates>() {

    override fun transform(item: PageablePremiereDates?): PremiereDatesModel {
        return PremiereDatesModel(item?.premiereDates?.map {
            PremiereDateModel(
                    id = it.id,
                    name = it.name,
                    imdbId = it.imdbId,
                    imageOriginal = it.imageOriginal,
                    imageMedium = it.imageMedium,
                    fullRuntime = it.fullRuntime,
                    status = it.status,
                    premiere = it.premiere,
                    episodeOrder = it.episodeOrder,
                    episodeRuntime = it.episodeRuntime,
                    genre = it.genre,
                    summary = it.summary)
        }, PageableRequest(
                last = item?.last!!,
                first = item.first,
                numberOfElements = item.numberOfElements,
                page = item.page,
                size = item.size,
                totalElements = item.totalElements,
                totalPages = item.totalPages))
    }
}
