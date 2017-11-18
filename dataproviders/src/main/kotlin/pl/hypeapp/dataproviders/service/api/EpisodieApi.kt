package pl.hypeapp.dataproviders.service.api

import io.reactivex.Single
import pl.hypeapp.dataproviders.entity.api.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EpisodieApi {

    @GET("tvshow/get/{tvShowId}")
    fun getTvShow(@Path("tvShowId") tvShowId: String): Single<TvShowEntity>

    @GET("tvshow/mostpopular")
    fun getMostPopular(@Query("page") page: Int, @Query("size") size: Int): Single<MostPopularEntity>

    @GET("tvshow/toplist")
    fun getTopList(@Query("page") page: Int, @Query("size") size: Int): Single<TopListEntity>

    @GET("tvshow/premieredates")
    fun getPremiereDates(@Query("page") page: Int, @Query("size") size: Int,
                         @Query("fromDate") fromDate: String): Single<PageablePremiereDates>

    @GET("tvshow/search?query={query}")
    fun search(@Path("query") query: String): Single<List<TvShowEntity>>

    @GET("tvshow/basic/search")
    fun basicSearch(@Query("query") query: String): Single<List<BasicSearchResultEntity>>

    @GET("tvshow/extended/get/{tvShowId}")
    fun getAllSeasons(@Path("tvShowId") tvShowId: String, @Query("afterPremiereDate") afterPremiereDate: Boolean)
            : Single<AllSeasonsEntity>

}
