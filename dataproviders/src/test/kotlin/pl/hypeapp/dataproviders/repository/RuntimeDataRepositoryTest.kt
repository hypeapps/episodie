package pl.hypeapp.dataproviders.repository

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import pl.hypeapp.dataproviders.datasource.DataFactory
import pl.hypeapp.dataproviders.datasource.UserStatsDataSource

class RuntimeDataRepositoryTest {

    private lateinit var userStatsDataRepository: UserStatsDataRepository

    private val dataFactory: DataFactory = mock()

    private val userStatsDataSource: UserStatsDataSource = mock()

    @Before
    fun setUp() {
//        userStatsDataRepository = UserStatsDataRepository(dataFactory)
//        given(dataFactory.createUserStatsDataSource()).willReturn(userStatsDataSource)
    }

    @Test
    fun `should get user full runtime`() {
//        val runtimeEntity = UserStatsEntity(23123L)
//        val userRuntime = Single.just(runtimeEntity)
//        given(userStatsDataSource.getUserFullRuntime()).willReturn(userRuntime)

        userStatsDataRepository.getUserFullRuntime()

        verify(dataFactory).createUserStatsDataSource()
        verify(userStatsDataSource).getUserFullRuntime()
    }

}
