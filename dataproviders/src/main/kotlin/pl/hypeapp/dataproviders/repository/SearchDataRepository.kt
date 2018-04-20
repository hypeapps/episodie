package pl.hypeapp.dataproviders.repository

import io.reactivex.Single
import pl.hypeapp.dataproviders.datasource.DataFactory
import pl.hypeapp.dataproviders.entity.mapper.tvshow.BasicSearchEntityMapper
import pl.hypeapp.domain.model.tvshow.TvShowModel
import pl.hypeapp.domain.repository.SearchRepository
import javax.inject.Inject

class SearchDataRepository @Inject constructor(private val dataFactory: DataFactory,
                                               private val basicSearchEntityMapper: BasicSearchEntityMapper)
    : SearchRepository {

    override fun basicSearch(query: String): Single<List<TvShowModel>> {
        return dataFactory
                .createTvShowDataSource()
                .basicSearch(query)
                .map {
                    basicSearchEntityMapper.transform(it)
                }
    }

}
