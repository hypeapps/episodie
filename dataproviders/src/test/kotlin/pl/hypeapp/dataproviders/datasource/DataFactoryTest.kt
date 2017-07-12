package pl.hypeapp.dataproviders.datasource

import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.`should not be`
import org.amshove.kluent.mock
import org.junit.Before
import org.junit.Test
import pl.hypeapp.dataproviders.cache.CacheProviders
import pl.hypeapp.dataproviders.cache.EvictCache
import pl.hypeapp.dataproviders.service.api.ApiService

class DataFactoryTest {

    private lateinit var dataFactory: DataFactory

    private val apiService: ApiService = mock()

    private val cacheProviders: CacheProviders = mock()

    private val evictCache: EvictCache = mock()

    @Before
    fun setUp() {
        dataFactory = DataFactory(apiService, cacheProviders, evictCache)
    }

    @Test
    fun `should create tv show data store`() {
        val dataStore: DataStore = dataFactory.createTvShowDataSource()
        dataStore `should not be` null
        dataStore `should be instance of` DataStore::class.java
    }

}
