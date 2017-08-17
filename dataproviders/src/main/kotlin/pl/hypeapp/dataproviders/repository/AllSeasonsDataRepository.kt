package pl.hypeapp.dataproviders.repository

import io.reactivex.Single
import pl.hypeapp.dataproviders.datasource.DataFactory
import pl.hypeapp.dataproviders.entity.mapper.AllSeasonsEntityMapper
import pl.hypeapp.domain.model.AllSeasonsModel
import pl.hypeapp.domain.repository.AllSeasonsRepository
import javax.inject.Inject

class AllSeasonsDataRepository @Inject constructor(private val dataFactory: DataFactory,
                                                   private val allSeasonsEntityMapper: AllSeasonsEntityMapper)
    : AllSeasonsRepository {

    override fun getAllSeasons(tvShowId: String, update: Boolean): Single<AllSeasonsModel> =
            dataFactory
                    .createTvShowDataSource()
                    .getAllSeasons(tvShowId, update)
                    .map { allSeasonsEntityMapper.transform(it) }

}
