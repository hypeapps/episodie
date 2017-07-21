package pl.hypeapp.dataproviders.service.api

import io.reactivex.Single
import pl.hypeapp.dataproviders.entity.MostPopularEntity
import pl.hypeapp.dataproviders.entity.TopListEntity
import pl.hypeapp.dataproviders.entity.TvShowEntity
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

    @GET("tvshow/search?query={query}")
    fun search(@Path("query") query: String): Single<List<TvShowEntity>>

}
