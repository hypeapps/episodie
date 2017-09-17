package pl.hypeapp.presentation.toplist

import com.nhaarman.mockito_kotlin.*
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.mockito.Mockito
import pl.hypeapp.domain.model.TopListModel
import pl.hypeapp.domain.model.TvShowModel
import pl.hypeapp.domain.usecase.mapwatched.TvShowWatchStateIntegrityUseCase
import pl.hypeapp.domain.usecase.runtime.UserRuntimeUseCase
import pl.hypeapp.domain.usecase.top.TopListUseCase
import pl.hypeapp.domain.usecase.watchstate.ManageTvShowWatchStateUseCase

class TopListPresenterTest {

    private lateinit var topListPresenter: TopListPresenter

    private val topListView: TopListView = mock()

    private val topListUseCase: TopListUseCase = mock()

    private val manageTvShowWatchStateUseCase: ManageTvShowWatchStateUseCase = mock()

    private val tvShowWatchStateIntegrityUseCase: TvShowWatchStateIntegrityUseCase = mock()

    private val userRuntimeUseCase: UserRuntimeUseCase = mock()

    companion object {
        val PAGE = 6
        val PAGE_LIMIT = 10
    }

    @Before
    fun setUp() {
        topListPresenter = TopListPresenter(topListUseCase, manageTvShowWatchStateUseCase,
                tvShowWatchStateIntegrityUseCase, userRuntimeUseCase)
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
    fun `should execute top list use case`() {
        topListPresenter.requestTopList(PAGE, false)
        verify(topListUseCase).execute(any(), any())
    }

    @Test
    fun `should not execute use case when page exceeds page limit`() {
        topListPresenter.requestTopList(PAGE_LIMIT, false)
        verifyZeroInteractions(topListUseCase)
    }

    @Test
    fun `should execute watch state integrity use case`() {
        val model: List<TvShowModel> = mock()

        topListPresenter.updateModel(model)

        verify(tvShowWatchStateIntegrityUseCase).execute(any(), any())
    }

    @Test
    @Ignore
    fun `should execute change watch state use case`() {
        topListPresenter.changeWatchedState(fakeModel)
        verify(manageTvShowWatchStateUseCase).execute(any(), any())
    }

    @Test
    fun `should animate drawer arrow`() {
        topListPresenter.view = topListView
        val drawerProgress = 0.92f

        topListPresenter.onDrawerDrag(drawerProgress)

        verify(topListView).animateDrawerHamburgerArrow(drawerProgress)
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

    private var fakeModel: TvShowModel = TvShowModel(
            id = "t23",
            summary = "Summary",
            name = "Game of Thrones",
            imageMedium = "http:image.com",
            imageOriginal = "http:imag2.com",
            status = "Running",
            network = "HBO",
            officialSite = "hbo.com",
            genre = "Drama",
            premiered = "12.12.2012",
            fullRuntime = 122L,
            episodeRuntime = 32323L,
            imdbId = "tt2323"
    )
}
