package pl.hypeapp.dataproviders.repository

import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import pl.hypeapp.dataproviders.datasource.DataFactory
import pl.hypeapp.dataproviders.datasource.RuntimeDataSource
import pl.hypeapp.dataproviders.entity.room.RuntimeEntity

class RuntimeDataRepositoryTest {

    private lateinit var runtimeDataRepository: RuntimeDataRepository

    private val dataFactory: DataFactory = mock()

    private val runtimeDataSource: RuntimeDataSource = mock()

    @Before
    fun setUp() {
        runtimeDataRepository = RuntimeDataRepository(dataFactory)
        given(dataFactory.createRuntimeDataSource()).willReturn(runtimeDataSource)
    }

    @Test
    fun `should get user full runtime`() {
        val runtimeEntity = RuntimeEntity(23123L)
        val userRuntime = Single.just(runtimeEntity)
        given(runtimeDataSource.getUserFullRuntime()).willReturn(userRuntime)

        runtimeDataRepository.getUserFullRuntime()

        verify(dataFactory).createRuntimeDataSource()
        verify(runtimeDataSource).getUserFullRuntime()
    }

}
