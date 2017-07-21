package pl.hypeapp.domain.usecase.top

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.verifyZeroInteractions
import org.junit.Before
import org.junit.Test
import pl.hypeapp.domain.executor.PostExecutionThread
import pl.hypeapp.domain.executor.ThreadExecutor
import pl.hypeapp.domain.repository.TopListRepository

class TopListUseCaseTest {

    private lateinit var topListUseCase: TopListUseCase

    private val repository: TopListRepository = mock()

    private val threadExecutor: ThreadExecutor = mock()

    private val postExecutionThread: PostExecutionThread = mock()

    companion object {
        val SIZE = 15
        val PAGE = 0
        val UPDATE = false
    }

    @Before
    fun setUp() {
        topListUseCase = TopListUseCase(threadExecutor, postExecutionThread, repository)
    }

    @Test
    fun shouldGetMostPopular() {
        val params: TopListUseCase.Params = TopListUseCase.Params.createQuery(PAGE, SIZE, UPDATE)

        topListUseCase.createSingle(params)

        verify(repository).getTopList(params.pageableRequest, UPDATE)
        verifyNoMoreInteractions(repository)
        verifyZeroInteractions(postExecutionThread)
        verifyZeroInteractions(threadExecutor)
    }

}
