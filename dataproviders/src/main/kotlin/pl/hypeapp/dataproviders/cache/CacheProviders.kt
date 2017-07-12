package pl.hypeapp.dataproviders.cache

import io.reactivex.Single
import io.rx_cache2.*
import pl.hypeapp.dataproviders.entity.MostPopularEntity
import pl.hypeapp.dataproviders.entity.TvShowEntity
import java.util.concurrent.TimeUnit

interface CacheProviders {

    @Expirable(true)
    fun getTvShow(tvShowEntity: Single<TvShowEntity>, query: DynamicKey, update: EvictDynamicKey): Single<TvShowEntity>

    @LifeCache(duration = 24, timeUnit = TimeUnit.HOURS)
    fun getMostPopular(mostPopularEntity: Single<MostPopularEntity>, query: DynamicKeyGroup, evictDynamicKeyGroup: EvictDynamicKeyGroup): Single<MostPopularEntity>

}
