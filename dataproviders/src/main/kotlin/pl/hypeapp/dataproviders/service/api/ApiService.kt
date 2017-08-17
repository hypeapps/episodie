package pl.hypeapp.dataproviders.service.api

import io.reactivex.Single
import pl.hypeapp.dataproviders.entity.AllSeasonsEntity
import pl.hypeapp.dataproviders.entity.MostPopularEntity
import pl.hypeapp.dataproviders.entity.TopListEntity
import pl.hypeapp.dataproviders.entity.TvShowEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiService @Inject constructor(private val episodieApi: EpisodieApi) : EpisodieApi {

    override fun getMostPopular(page: Int, size: Int): Single<MostPopularEntity> {
        return episodieApi.getMostPopular(page, size)
    }

    override fun getTopList(page: Int, size: Int): Single<TopListEntity> {
        return episodieApi.getTopList(page, size)
    }

    override fun search(query: String): Single<List<TvShowEntity>> {
        return episodieApi.search(query)
    }

    override fun getTvShow(tvShowId: String): Single<TvShowEntity> {
        return episodieApi.getTvShow(tvShowId)
    }

    override fun getAllSeasons(tvShowId: String): Single<AllSeasonsEntity> {
        return episodieApi.getAllSeasons(tvShowId)
    }

}
