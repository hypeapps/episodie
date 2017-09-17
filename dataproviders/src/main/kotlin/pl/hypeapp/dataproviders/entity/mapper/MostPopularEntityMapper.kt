package pl.hypeapp.dataproviders.entity.mapper

import pl.hypeapp.dataproviders.entity.api.MostPopularEntity
import pl.hypeapp.domain.model.MostPopularModel
import pl.hypeapp.domain.model.PageableRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MostPopularEntityMapper @Inject constructor() : EntityMapper<MostPopularModel, MostPopularEntity>() {

    override fun transform(entity: MostPopularEntity?): MostPopularModel {
        return MostPopularModel(TvShowEntityMapper().transform(entity!!.tvShows),
                PageableRequest(entity.last,
                        entity.totalPages,
                        entity.totalElements,
                        entity.size,
                        entity.page,
                        entity.first,
                        entity.numberOfElements))
    }

}
