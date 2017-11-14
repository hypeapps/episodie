package pl.hypeapp.dataproviders.datasource

import io.reactivex.Single
import io.rx_cache2.DynamicKey
import io.rx_cache2.DynamicKeyGroup
import io.rx_cache2.EvictDynamicKey
import io.rx_cache2.EvictDynamicKeyGroup
import pl.hypeapp.dataproviders.cache.CacheProviders
import pl.hypeapp.dataproviders.cache.EvictCache
import pl.hypeapp.dataproviders.entity.api.*
import pl.hypeapp.dataproviders.service.api.ApiService
import pl.hypeapp.domain.model.PageableRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TvShowDataSource @Inject constructor(private val apiService: ApiService,
                                           private val cacheProviders: CacheProviders,
                                           private val evictCache: EvictCache) : TvShowDataStore {

    override fun getTvShow(tvShowId: String, update: Boolean): Single<TvShowEntity> =
            cacheProviders.getTvShow(apiService.getTvShow(tvShowId), DynamicKey(tvShowId), EvictDynamicKey(update))

    override fun getMostPopular(pageableRequest: PageableRequest, update: Boolean): Single<MostPopularEntity> {
        if (update) evictCache.evictAllMatchingDynamicKey(DynamicKey(MOST_POPULAR_KEY))
        return cacheProviders.getMostPopular(apiService.getMostPopular(pageableRequest.page,
                pageableRequest.size), DynamicKeyGroup(MOST_POPULAR_KEY, pageableRequest.page),
                        EvictDynamicKeyGroup(update))
    }

    override fun getTopList(pageableRequest: PageableRequest, update: Boolean): Single<TopListEntity> {
        if (update) evictCache.evictAllMatchingDynamicKey(DynamicKey(TOP_LIST_KEY))
        return cacheProviders.getTopList(apiService.getTopList(pageableRequest.page,
                pageableRequest.size), DynamicKeyGroup(TOP_LIST_KEY, pageableRequest.page),
                EvictDynamicKeyGroup(update))
    }

    override fun getAllSeasonsAfterPremiereDate(tvShowId: String, update: Boolean): Single<AllSeasonsEntity> {
        return cacheProviders.getAllSeasonsAfterPremiereDate(apiService.getAllSeasons(tvShowId, true),
                DynamicKey(tvShowId), EvictDynamicKey(update))
    }

    override fun getAllSeasons(tvShowId: String, update: Boolean): Single<AllSeasonsEntity> {
        return cacheProviders.getAllSeasons(apiService.getAllSeasons(tvShowId, false),
                DynamicKey(tvShowId), EvictDynamicKey(update))
    }

    override fun basicSearch(query: String): Single<List<BasicSearchResultEntity>> = apiService.basicSearch(query)

    private companion object {
        val MOST_POPULAR_KEY = "MP"
        val TOP_LIST_KEY = "TL"
    }

}
