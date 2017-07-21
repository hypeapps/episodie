package pl.hypeapp.dataproviders.service.api

import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Single
import org.amshove.kluent.`should equal`
import org.amshove.kluent.any
import org.amshove.kluent.mock
import org.junit.Before
import org.junit.Test
import pl.hypeapp.dataproviders.entity.MostPopularEntity
import pl.hypeapp.dataproviders.entity.TopListEntity

class ApiServiceTest {

    private lateinit var apiService: ApiService

    private val episodieApi: EpisodieApi = mock()

    @Before
    fun setUp() {
        apiService = ApiService(episodieApi)
    }

    @Test
    fun `should get most popular`() {
        val mostPopularEntity: Single<MostPopularEntity> = mock()
        given(episodieApi.getMostPopular(any(), any())).willReturn(mostPopularEntity)

        val response = apiService.getMostPopular(any(), any())

        verify(episodieApi).getMostPopular(any(), any())
        mostPopularEntity `should equal` response
    }

    @Test
    fun `should get top list`() {
        val topListEntity: Single<TopListEntity> = mock()
        given(episodieApi.getTopList(any(), any())).willReturn(topListEntity)

        val response = apiService.getTopList(any(), any())

        verify(episodieApi).getTopList(any(), any())
        topListEntity `should equal` response
    }

}
