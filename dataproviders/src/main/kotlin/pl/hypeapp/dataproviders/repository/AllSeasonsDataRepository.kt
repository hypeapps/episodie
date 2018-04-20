package pl.hypeapp.dataproviders.repository

import io.reactivex.Single
import pl.hypeapp.dataproviders.datasource.DataFactory
import pl.hypeapp.dataproviders.entity.mapper.tvshow.AllSeasonsEntityMapper
import pl.hypeapp.domain.model.tvshow.TvShowExtendedModel
import pl.hypeapp.domain.repository.AllSeasonsRepository
import javax.inject.Inject

class AllSeasonsDataRepository @Inject constructor(private val dataFactory: DataFactory,
                                                   private val allSeasonsEntityMapper: AllSeasonsEntityMapper)
    : AllSeasonsRepository {

    override fun getAllSeasonsAfterPremiereDate(tvShowId: String, update: Boolean): Single<TvShowExtendedModel> =
            dataFactory
                    .createTvShowDataSource()
                    .getAllSeasonsAfterPremiereDate(tvShowId, update)
                    .map { allSeasonsEntityMapper.transform(it) }

    override fun getAllSeason(tvShowId: String, update: Boolean): Single<TvShowExtendedModel> =
            dataFactory
                    .createTvShowDataSource()
                    .getAllSeasons(tvShowId, update)
                    .map { allSeasonsEntityMapper.transform(it) }

}
