package pl.hypeapp.dataproviders.datasource

import io.reactivex.Single
import pl.hypeapp.dataproviders.entity.MostPopularEntity
import pl.hypeapp.dataproviders.entity.TvShowEntity
import pl.hypeapp.domain.model.PageableRequest

interface DataStore {

    fun getTvShow(tvShowId: String, update: Boolean): Single<TvShowEntity>

    fun getMostPopular(pageableRequest: PageableRequest, update: Boolean): Single<MostPopularEntity>

}
