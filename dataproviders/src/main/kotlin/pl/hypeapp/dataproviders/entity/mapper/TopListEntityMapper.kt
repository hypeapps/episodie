package pl.hypeapp.dataproviders.entity.mapper

import pl.hypeapp.dataproviders.entity.TopListEntity
import pl.hypeapp.domain.model.PageableRequest
import pl.hypeapp.domain.model.TopListModel
import javax.inject.Inject

class TopListEntityMapper @Inject constructor() : EntityMapper<TopListModel, TopListEntity>() {

    override fun transform(entity: TopListEntity?): TopListModel {
        return TopListModel(TvShowEntityMapper().transform(entity!!.tvShows),
                PageableRequest(entity.last,
                        entity.totalPages,
                        entity.totalElements,
                        entity.size,
                        entity.page,
                        entity.first,
                        entity.numberOfElements))
    }

}
