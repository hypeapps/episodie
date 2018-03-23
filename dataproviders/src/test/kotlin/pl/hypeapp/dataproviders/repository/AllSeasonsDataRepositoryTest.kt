package pl.hypeapp.dataproviders.repository

import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Single
import org.amshove.kluent.mock
import org.junit.Before
import org.junit.Test
import pl.hypeapp.dataproviders.datasource.DataFactory
import pl.hypeapp.dataproviders.datasource.TvShowDataStore
import pl.hypeapp.dataproviders.entity.api.AllSeasonsEntity
import pl.hypeapp.dataproviders.entity.mapper.tvshow.AllSeasonsEntityMapper

class AllSeasonsDataRepositoryTest {

    private lateinit var dataRepository: AllSeasonsDataRepository

    private val dataFactory: DataFactory = mock()

    private val allSeasonsEntityMapper: AllSeasonsEntityMapper = mock()

    private val tvShowDataStore: TvShowDataStore = mock()

    @Before
    fun setUp() {
        dataRepository = AllSeasonsDataRepository(dataFactory, allSeasonsEntityMapper)
        given(dataFactory.createTvShowDataSource()).willReturn(tvShowDataStore)
    }

    @Test
    fun `should get all seasons`() {
        val allSeasonsEntity: AllSeasonsEntity = mock()
        val single: Single<AllSeasonsEntity> = Single.just(allSeasonsEntity)
        val tvShowId = "12"
        val update = true
        given(tvShowDataStore.getAllSeasonsAfterPremiereDate(tvShowId, update)).willReturn(single)

        dataRepository.getAllSeasonsAfterPremiereDate(tvShowId, update)

        verify(dataFactory).createTvShowDataSource()
        verify(tvShowDataStore).getAllSeasonsAfterPremiereDate(tvShowId, update)
    }

}
