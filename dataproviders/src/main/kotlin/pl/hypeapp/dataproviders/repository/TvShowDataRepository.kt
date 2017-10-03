package pl.hypeapp.dataproviders.repository

import io.reactivex.Single
import pl.hypeapp.dataproviders.datasource.DataFactory
import pl.hypeapp.dataproviders.entity.mapper.TvShowEntityMapper
import pl.hypeapp.domain.model.TvShowModel
import pl.hypeapp.domain.repository.TvShowRepository
import javax.inject.Inject

class TvShowDataRepository @Inject constructor(private val dataFactory: DataFactory,
                                               private val tvShowEntityMapper: TvShowEntityMapper) : TvShowRepository {

    override fun getTvShow(tvShowId: String, update: Boolean): Single<TvShowModel> {
        return dataFactory
                .createTvShowDataSource()
                .getTvShow(tvShowId, update)
                .map { tvShowEntityMapper.transform(it) }
    }

}
