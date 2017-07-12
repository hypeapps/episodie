package pl.hypeapp.dataproviders.repository

import io.reactivex.Single
import pl.hypeapp.dataproviders.datasource.DataFactory
import pl.hypeapp.dataproviders.entity.mapper.MostPopularEntityMapper
import pl.hypeapp.domain.model.MostPopularModel
import pl.hypeapp.domain.model.PageableRequest
import pl.hypeapp.domain.repository.MostPopularRepository
import javax.inject.Inject

class MostPopularDataRepository @Inject constructor(private val dataFactory: DataFactory,
                                                    private val mostPopularEntityMapper: MostPopularEntityMapper)
    : MostPopularRepository {

    override fun getMostPopular(pageableRequest: PageableRequest, update: Boolean): Single<MostPopularModel> =
            dataFactory
                    .createTvShowDataSource()
                    .getMostPopular(pageableRequest, update)
                    .map { mostPopularEntityMapper.transform(it) }

}
