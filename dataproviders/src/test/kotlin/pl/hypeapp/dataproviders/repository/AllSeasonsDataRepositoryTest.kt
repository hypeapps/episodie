package pl.hypeapp.dataproviders.repository

import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Single
import org.amshove.kluent.mock
import org.junit.Before
import org.junit.Test
import pl.hypeapp.dataproviders.datasource.DataFactory
import pl.hypeapp.dataproviders.datasource.DataStore
import pl.hypeapp.dataproviders.entity.AllSeasonsEntity
import pl.hypeapp.dataproviders.entity.mapper.AllSeasonsEntityMapper

class AllSeasonsDataRepositoryTest {

    private lateinit var dataRepository: AllSeasonsDataRepository

    private val dataFactory: DataFactory = mock()

    private val allSeasonsEntityMapper: AllSeasonsEntityMapper = mock()

    private val dataStore: DataStore = mock()

    @Before
    fun setUp() {
        dataRepository = AllSeasonsDataRepository(dataFactory, allSeasonsEntityMapper)
        given(dataFactory.createTvShowDataSource()).willReturn(dataStore)
    }

    @Test
    fun `should get all seasons`() {
        val allSeasonsEntity: AllSeasonsEntity = mock()
        val single: Single<AllSeasonsEntity> = Single.just(allSeasonsEntity)
        val tvShowId = "12"
        val update = true
        given(dataStore.getAllSeasons(tvShowId, update)).willReturn(single)

        dataRepository.getAllSeasons(tvShowId, update)

        verify(dataFactory).createTvShowDataSource()
        verify(dataStore).getAllSeasons(tvShowId, update)
    }

}
