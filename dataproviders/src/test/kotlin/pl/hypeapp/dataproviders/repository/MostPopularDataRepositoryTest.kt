package pl.hypeapp.dataproviders.repository

import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Single
import org.amshove.kluent.mock
import org.junit.Before
import org.junit.Test
import pl.hypeapp.dataproviders.datasource.DataFactory
import pl.hypeapp.dataproviders.datasource.DataStore
import pl.hypeapp.dataproviders.entity.MostPopularEntity
import pl.hypeapp.dataproviders.entity.mapper.MostPopularEntityMapper
import pl.hypeapp.domain.model.PageableRequest

class MostPopularDataRepositoryTest {

    private lateinit var mostPopularDataRepository: MostPopularDataRepository

    private val dataFactory: DataFactory = mock()

    private val mostPopularEntityMapper: MostPopularEntityMapper = mock()

    private val dataStore: DataStore = mock()

    @Before
    fun setUp() {
        mostPopularDataRepository = MostPopularDataRepository(dataFactory, mostPopularEntityMapper)
        given(dataFactory.createTvShowDataSource()).willReturn(dataStore)
    }

    @Test
    fun `shouldGetMostPopular`() {
        val mostPopularEntity: MostPopularEntity = mock()
        val pageableRequest: PageableRequest = mock()
        val update = false
        given(dataStore.getMostPopular(pageableRequest, update)).willReturn(Single.just(mostPopularEntity))

        mostPopularDataRepository.getMostPopular(pageableRequest, update)

        verify(dataFactory).createTvShowDataSource()
        verify(dataStore).getMostPopular(pageableRequest, update)
    }

}
