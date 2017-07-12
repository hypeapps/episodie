package pl.hypeapp.domain.usecase.mostpopular

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.verifyZeroInteractions
import org.junit.Before
import org.junit.Test
import pl.hypeapp.domain.executor.PostExecutionThread
import pl.hypeapp.domain.executor.ThreadExecutor
import pl.hypeapp.domain.repository.MostPopularRepository

class MostPopularUseCaseTest {

    private lateinit var mostPopularUseCase: MostPopularUseCase

    private val repository: MostPopularRepository = mock()

    private val threadExecutor: ThreadExecutor = mock()

    private val postExecutionThread: PostExecutionThread = mock()

    companion object {
        val SIZE = 15
        val PAGE = 0
        val UPDATE = false
    }

    @Before
    fun setUp() {
        mostPopularUseCase = MostPopularUseCase(threadExecutor, postExecutionThread, repository)
    }

    @Test
    fun shouldGetMostPopular() {
        val params: MostPopularUseCase.Params = MostPopularUseCase.Params.createQuery(PAGE, SIZE, UPDATE)

        mostPopularUseCase.createSingle(params)

        verify(repository).getMostPopular(params.pageableRequest, UPDATE)
        verifyNoMoreInteractions(repository)
        verifyZeroInteractions(postExecutionThread)
        verifyZeroInteractions(threadExecutor)
    }

}
