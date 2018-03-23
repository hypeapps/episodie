package pl.hypeapp.dataproviders.entity.mapper.tvshow

import pl.hypeapp.dataproviders.entity.api.MostPopularEntity
import pl.hypeapp.dataproviders.entity.mapper.Mapper
import pl.hypeapp.domain.model.PageableRequest
import pl.hypeapp.domain.model.collections.MostPopularModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MostPopularEntityMapper @Inject constructor() : Mapper<MostPopularModel, MostPopularEntity>() {

    override fun transform(item: MostPopularEntity?): MostPopularModel {
        return MostPopularModel(TvShowEntityMapper().transform(item!!.tvShows),
                PageableRequest(item.last,
                        item.totalPages,
                        item.totalElements,
                        item.size,
                        item.page,
                        item.first,
                        item.numberOfElements))
    }

}
