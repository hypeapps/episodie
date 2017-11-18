package pl.hypeapp.dataproviders.datasource

import io.reactivex.Single
import pl.hypeapp.dataproviders.entity.api.*
import pl.hypeapp.domain.model.PageableRequest
import java.util.*

interface TvShowDataStore {

    fun getTvShow(tvShowId: String, update: Boolean): Single<TvShowEntity>

    fun getMostPopular(pageableRequest: PageableRequest, update: Boolean): Single<MostPopularEntity>

    fun getTopList(pageableRequest: PageableRequest, update: Boolean): Single<TopListEntity>

    fun getAllSeasonsAfterPremiereDate(tvShowId: String, update: Boolean): Single<AllSeasonsEntity>

    fun getAllSeasons(tvShowId: String, update: Boolean): Single<AllSeasonsEntity>

    fun basicSearch(query: String): Single<List<BasicSearchResultEntity>>

    fun getPremiereDates(pageableRequest: PageableRequest, fromDate: Date, update: Boolean): Single<PageablePremiereDates>

}
