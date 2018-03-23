package pl.hypeapp.dataproviders.entity.mapper.tvshow

import pl.hypeapp.dataproviders.entity.api.TopListEntity
import pl.hypeapp.dataproviders.entity.mapper.Mapper
import pl.hypeapp.domain.model.PageableRequest
import pl.hypeapp.domain.model.collections.TopListModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopListEntityMapper @Inject constructor() : Mapper<TopListModel, TopListEntity>() {

    override fun transform(item: TopListEntity?): TopListModel {
        return TopListModel(TvShowEntityMapper().transform(item!!.tvShows),
                PageableRequest(item.last,
                        item.totalPages,
                        item.totalElements,
                        item.size,
                        item.page,
                        item.first,
                        item.numberOfElements))
    }

}
