package pl.hypeapp.dataproviders.entity.mapper

import pl.hypeapp.dataproviders.entity.api.PageablePremiereDates
import pl.hypeapp.domain.model.PageableRequest
import pl.hypeapp.domain.model.PremiereDateModel
import pl.hypeapp.domain.model.PremiereDatesModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PremiereDatesEntityMapper @Inject constructor() : EntityMapper<PremiereDatesModel, PageablePremiereDates>() {

    override fun transform(entity: PageablePremiereDates?): PremiereDatesModel {
        return PremiereDatesModel(entity?.premiereDates?.map {
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
                last = entity?.last!!,
                first = entity.first,
                numberOfElements = entity.numberOfElements,
                page = entity.page,
                size = entity.size,
                totalElements = entity.totalElements,
                totalPages = entity.totalPages))
    }
}
