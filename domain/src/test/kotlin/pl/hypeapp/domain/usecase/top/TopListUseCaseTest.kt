package pl.hypeapp.domain.usecase.top

import com.nhaarman.mockito_kotlin.*
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import pl.hypeapp.domain.executor.PostExecutionThread
import pl.hypeapp.domain.executor.ThreadExecutor
import pl.hypeapp.domain.model.collections.TopListModel
import pl.hypeapp.domain.repository.TopListRepository
import pl.hypeapp.domain.usecase.watchstate.mapwatched.WatchStateMapper

class TopListUseCaseTest {

    private lateinit var topListUseCase: TopListUseCase

    private val repository: TopListRepository = mock()

    private val watchStateMapper: WatchStateMapper = mock()

    private val threadExecutor: ThreadExecutor = mock()

    private val postExecutionThread: PostExecutionThread = mock()

    companion object {
        val SIZE = 15
        val PAGE = 0
        val UPDATE = false
    }

    @Before
    fun setUp() {
        topListUseCase = TopListUseCase(threadExecutor, postExecutionThread, watchStateMapper, repository)
    }

    @Test
    fun shouldGetMostPopular() {
        val params: TopListUseCase.Params = TopListUseCase.Params.createQuery(PAGE, SIZE, UPDATE)
        val topListModel: TopListModel = mock()
        given(repository.getTopList(params.pageableRequest, params.update)).willReturn(Single.just(topListModel))

        topListUseCase.createSingle(params).blockingGet()

        verify(repository).getTopList(params.pageableRequest, UPDATE)
        verify(watchStateMapper).map(topListModel.tvShows)
        verifyNoMoreInteractions(watchStateMapper)
        verifyNoMoreInteractions(repository)
        verifyZeroInteractions(postExecutionThread)
        verifyZeroInteractions(threadExecutor)
    }

}
