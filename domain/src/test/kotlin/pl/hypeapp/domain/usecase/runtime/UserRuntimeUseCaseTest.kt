package pl.hypeapp.domain.usecase.runtime

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.verifyZeroInteractions
import org.junit.Before
import org.junit.Test
import pl.hypeapp.domain.executor.PostExecutionThread
import pl.hypeapp.domain.executor.ThreadExecutor
import pl.hypeapp.domain.repository.RuntimeRepository

class UserRuntimeUseCaseTest {

    private lateinit var userRuntimeUseCase: UserRuntimeUseCase

    private val repository: RuntimeRepository = mock()

    private val threadExecutor: ThreadExecutor = mock()

    private val postExecutionThread: PostExecutionThread = mock()

    @Before
    fun setUp() {
        userRuntimeUseCase = UserRuntimeUseCase(threadExecutor, postExecutionThread, repository)
    }

    @Test
    fun `should get user runtime`() {
        userRuntimeUseCase.createSingle(null)

        verify(repository).getUserFullRuntime()
        verifyNoMoreInteractions(repository)
        verifyZeroInteractions(postExecutionThread)
        verifyZeroInteractions(threadExecutor)
    }

}
