package pl.hypeapp.domain.usecase.seasons

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import org.junit.Before
import org.junit.Test
import pl.hypeapp.domain.executor.PostExecutionThread
import pl.hypeapp.domain.executor.ThreadExecutor
import pl.hypeapp.domain.repository.AllSeasonsRepository

class SeasonsUseCaseTest {

    private lateinit var useCase: SeasonsUseCase

    private val repository: AllSeasonsRepository = mock()

    private val threadExecutor: ThreadExecutor = mock()

    private val postExecutionThread: PostExecutionThread = mock()

    companion object {
        val ID = "12231"
        val UPDATE = true
    }

    @Before
    fun setUp() {
        useCase = SeasonsUseCase(threadExecutor, postExecutionThread, repository)
    }

    @Test
    fun `should get all seasons`() {
        val params: SeasonsUseCase.Params = SeasonsUseCase.Params.createQuery(ID, UPDATE)

        useCase.createSingle(params)

        verify(repository).getAllSeasons(ID, UPDATE)
        verifyNoMoreInteractions(repository)
    }

}
