package pl.hypeapp.dataproviders.datasource

import pl.hypeapp.dataproviders.cache.CacheProviders
import pl.hypeapp.dataproviders.cache.EvictCache
import pl.hypeapp.dataproviders.service.api.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataFactory @Inject constructor(private val apiService: ApiService,
                                      private val cacheProviders: CacheProviders,
                                      private val evictCache: EvictCache) {

    fun createTvShowDataSource(): DataStore = DataSource(apiService, cacheProviders, evictCache)

}
