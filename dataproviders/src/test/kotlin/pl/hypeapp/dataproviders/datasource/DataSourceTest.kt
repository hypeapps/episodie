package pl.hypeapp.dataproviders.datasource

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyZeroInteractions
import io.reactivex.Single
import org.amshove.kluent.mock
import org.junit.Before
import org.junit.Test
import pl.hypeapp.dataproviders.cache.CacheProviders
import pl.hypeapp.dataproviders.cache.EvictCache
import pl.hypeapp.dataproviders.entity.AllSeasonsEntity
import pl.hypeapp.dataproviders.entity.MostPopularEntity
import pl.hypeapp.dataproviders.entity.TopListEntity
import pl.hypeapp.dataproviders.service.api.ApiService
import pl.hypeapp.domain.model.PageableRequest

class DataSourceTest {

    private lateinit var dataSource: DataSource

    private val apiService: ApiService = mock()

    private val cacheProviders: CacheProviders = mock()

    private val evictCache: EvictCache = mock()

    private val pageableRequest: PageableRequest = mock()

    @Before
    fun setUp() {
        dataSource = DataSource(apiService, cacheProviders, evictCache)
    }

    @Test
    fun `should get most popular`() {
        val mostPopularEntity: Single<MostPopularEntity> = mock()
        given(apiService.getMostPopular(pageableRequest.page, pageableRequest.size)).willReturn(mostPopularEntity)

        dataSource.getMostPopular(pageableRequest, false)

        verify(cacheProviders).getMostPopular(any(), any(), any())
        verify(apiService).getMostPopular(pageableRequest.page, pageableRequest.size)
    }

    @Test
    fun `should evict most popular`() {
        dataSource.getMostPopular(pageableRequest, true)

        verify(evictCache).evictAllMatchingDynamicKey(any())
    }

    @Test
    fun `should not evict most popular`() {
        dataSource.getMostPopular(pageableRequest, false)

        verifyZeroInteractions(evictCache)
    }

    @Test
    fun `should get top list`() {
        val topListEntity: Single<TopListEntity> = mock()
        given(apiService.getTopList(pageableRequest.page, pageableRequest.size)).willReturn(topListEntity)

        dataSource.getTopList(pageableRequest, false)

        verify(cacheProviders).getTopList(any(), any(), any())
        verify(apiService).getTopList(pageableRequest.page, pageableRequest.size)
    }

    @Test
    fun `should evict top list`() {
        dataSource.getTopList(pageableRequest, true)

        verify(evictCache).evictAllMatchingDynamicKey(any())
    }

    @Test
    fun `should not evict top list`() {
        dataSource.getTopList(pageableRequest, false)

        verifyZeroInteractions(evictCache)
    }

    @Test
    fun `should get all seasons`() {
        val allSeasonsEntity: Single<AllSeasonsEntity> = mock()
        val tvShowId = "12"
        given(apiService.getAllSeasons(tvShowId)).willReturn(allSeasonsEntity)

        dataSource.getAllSeasons(tvShowId, false)

        verify(cacheProviders).getAllSeasons(any(), any(), any())
        verify(apiService).getAllSeasons(tvShowId)
    }

}
