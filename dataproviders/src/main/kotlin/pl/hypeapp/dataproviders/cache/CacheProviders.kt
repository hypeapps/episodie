package pl.hypeapp.dataproviders.cache

import io.reactivex.Single
import io.rx_cache2.*
import pl.hypeapp.dataproviders.entity.api.AllSeasonsEntity
import pl.hypeapp.dataproviders.entity.api.MostPopularEntity
import pl.hypeapp.dataproviders.entity.api.TopListEntity
import pl.hypeapp.dataproviders.entity.api.TvShowEntity
import java.util.concurrent.TimeUnit

interface CacheProviders {

    @Expirable(true)
    fun getTvShow(tvShowEntity: Single<TvShowEntity>, query: DynamicKey, update: EvictDynamicKey): Single<TvShowEntity>

    @LifeCache(duration = 1, timeUnit = TimeUnit.DAYS)
    fun getMostPopular(mostPopularEntity: Single<MostPopularEntity>, query: DynamicKeyGroup, evictDynamicKeyGroup: EvictDynamicKeyGroup): Single<MostPopularEntity>

    @LifeCache(duration = 1, timeUnit = TimeUnit.DAYS)
    fun getTopList(topListEntity: Single<TopListEntity>, query: DynamicKeyGroup, evictDynamicKeyGroup: EvictDynamicKeyGroup): Single<TopListEntity>

    @LifeCache(duration = 1, timeUnit = TimeUnit.DAYS)
    fun getAllSeasonsAfterPremiereDate(allSeasonsEntity: Single<AllSeasonsEntity>, query: DynamicKey, evictDynamicKey: EvictDynamicKey): Single<AllSeasonsEntity>

    @LifeCache(duration = 1, timeUnit = TimeUnit.DAYS)
    fun getAllSeasons(allSeasonsEntity: Single<AllSeasonsEntity>, query: DynamicKey, evictDynamicKey: EvictDynamicKey): Single<AllSeasonsEntity>

}
