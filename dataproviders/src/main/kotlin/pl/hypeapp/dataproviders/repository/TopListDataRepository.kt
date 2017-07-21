package pl.hypeapp.dataproviders.repository

import io.reactivex.Single
import pl.hypeapp.dataproviders.datasource.DataFactory
import pl.hypeapp.dataproviders.entity.mapper.TopListEntityMapper
import pl.hypeapp.domain.model.PageableRequest
import pl.hypeapp.domain.model.TopListModel
import pl.hypeapp.domain.repository.TopListRepository
import javax.inject.Inject

class TopListDataRepository @Inject constructor(private val dataFactory: DataFactory,
                                                private val topListEntityMapper: TopListEntityMapper)
    : TopListRepository {

    override fun getTopList(pageableRequest: PageableRequest, update: Boolean): Single<TopListModel> =
            dataFactory
                    .createTvShowDataSource()
                    .getTopList(pageableRequest, update)
                    .map { topListEntityMapper.transform(it) }

}
