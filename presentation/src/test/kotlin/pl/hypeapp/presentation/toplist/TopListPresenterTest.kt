package pl.hypeapp.presentation.toplist

import com.nhaarman.mockito_kotlin.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import pl.hypeapp.domain.model.TopListModel
import pl.hypeapp.domain.usecase.top.TopListUseCase

class TopListPresenterTest {

    private lateinit var topListPresenter: TopListPresenter

    private val topListView: TopListView = mock()

    private val topListUseCase: TopListUseCase = mock()

    companion object {
        val PAGE = 6
        val PAGE_LIMIT = 10
    }

    @Before
    fun setUp() {
        topListPresenter = TopListPresenter(topListUseCase)
    }

    @Test
    fun `should load view model`() {
        topListPresenter.onAttachView(topListView)
        verify(topListView).loadViewModel()
    }

    @Test
    fun `should dispose use case`() {
        topListPresenter.onDetachView()
        verify(topListUseCase).dispose()
    }

    @Test
    fun `should execute use case`() {
        topListPresenter.requestTopList(PAGE, false)
        verify(topListUseCase).execute(any(), any())
    }

    @Test
    fun `should not execute use case when page exceeds page limit`() {
        topListPresenter.requestTopList(PAGE_LIMIT, false)
        verifyZeroInteractions(topListUseCase)
    }

    @Test
    fun `should pass model and populate recycler`() {
        topListPresenter.view = topListView
        val topListModel: TopListModel = mock()

        Mockito.`when`(topListUseCase.execute(any(), any())).thenAnswer({
            (it.arguments[0] as TopListPresenter.TopListObserver).onSuccess(topListModel)
        })

        topListPresenter.requestTopList(4, true)

        verify(topListView, times(1)).populateRecyclerList(topListModel)
    }

    @Test
    fun `should show error`() {
        topListPresenter.view = topListView
        val throwable: Throwable = mock()

        Mockito.`when`(topListUseCase.execute(any(), any())).thenAnswer({
            (it.arguments[0] as TopListPresenter.TopListObserver).onError(throwable)
        })

        topListPresenter.requestTopList(4, true)

        verify(topListView, times(1)).showError()
    }
}