package pl.hypeapp.presentation.tvshowdetails

import com.nhaarman.mockito_kotlin.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import pl.hypeapp.domain.model.tvshow.TvShowModel
import pl.hypeapp.domain.usecase.userstats.UserRuntimeUseCase
import pl.hypeapp.domain.usecase.watchstate.UpdateTvShowWatchStateByIdUseCase
import pl.hypeapp.domain.usecase.watchstate.mapwatched.MapTvShowsWatchStateUseCase

class TvShowDetailsPresenterTest {

    private lateinit var presenter: TvShowDetailsPresenter

    private val view: TvShowDetailsView = mock()

    private val updateTvShowWatchStateByIdUseCase: UpdateTvShowWatchStateByIdUseCase = mock()

    private val mapTvShowsWatchStateUseCase: MapTvShowsWatchStateUseCase = mock()

    private val userRuntimeUseCase: UserRuntimeUseCase = mock()

    @Before
    fun setUp() {
        presenter = TvShowDetailsPresenter(updateTvShowWatchStateByIdUseCase,
                mapTvShowsWatchStateUseCase,
                userRuntimeUseCase)
    }

    @Test
    fun `should get model`() {
        `when`(view.model).thenReturn(fakeModel)

        presenter.onAttachView(view)

        verify(view, times(2)).model

        val model = view.model
        assertEquals(model, fakeModel)
    }

    @Test
    fun `should expand app bar`() {
        presenter.view = view

        presenter.onPageSelected(0)

        verify(view).expandAppBar()
    }

    @Test
    fun `should not expand bar`() {
        presenter.view = view

        presenter.onPageSelected(1)

        verifyZeroInteractions(view)
    }

    @Test
    fun `should start fab button animation on pager adapter init`() {
        presenter.view = view

        presenter.onPagerAdapterInit()

        verify(view).startFabButtonAnimation()
    }

    @Test
    fun `should fill tv show details info `() {
        `when`(view.model).thenReturn(fakeModel)

        presenter.onAttachView(view)

        verify(view).setBackdrop(fakeModel.imageOriginal, fakeModel.imageMedium)
        verify(view).setCover(fakeModel.imageMedium)
        verify(view).setFullRuntime(fakeModel.fullRuntime)
        verify(view).setPremiered(fakeModel.premiered)
        verify(view).setStatus(fakeModel.status)
        verify(view).setTitle(fakeModel.name)
    }

    @Test
    fun `should apply navigation bar options`() {
        `when`(view.model).thenReturn(fakeModel)
        presenter.onAttachView(view)

        verify(view).setNavigationBarOptions()
    }

    @Test
    fun `should init pager adapter`() {
        `when`(view.model).thenReturn(fakeModel)

        presenter.onAttachView(view)

        verify(view).initPagerAdapter(fakeModel)
    }

    @Test
    fun `should execute watch state integrity use case`() {
        `when`(view.model).thenReturn(fakeModel)

        presenter.onAttachView(view)
        presenter.updateWatchState()

        verify(mapTvShowsWatchStateUseCase, times(2)).execute(any(), any())
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
