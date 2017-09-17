package pl.hypeapp.presentation.tvshowsummary

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import pl.hypeapp.domain.model.TvShowModel

class TvShowSummaryPresenterTest {

    private lateinit var presenter: TvShowSummaryPresenter

    private val view: TvShowSummaryView = mock()

    @Before
    fun setUp() {
        presenter = TvShowSummaryPresenter()
        `when`(view.getModel()).thenReturn(fakeModel)
    }

    @Test
    fun `should get model`() {
        presenter.onAttachView(view)

        verify(view).getModel()

        val model = view.getModel()
        assertEquals(model, fakeModel)
    }

    @Test
    fun `should start intent browser on official site pressed`() {
        presenter.view = view

        presenter.onOfficialSitePressed()

        verify(view).openBrowserIntent(fakeModel.officialSite)
    }

    @Test
    fun `should start intent browser on imdb site pressed`() {
        presenter.view = view

        presenter.onImdbSitePressed()

        verify(view).openBrowserIntent(TvShowSummaryPresenter.URL_IMDB + fakeModel.imdbId)
    }

    @Test
    fun `should start intent browser on tv maze site pressed`() {
        presenter.view = view

        presenter.onTvMazeSitePressed()

        verify(view).openBrowserIntent(TvShowSummaryPresenter.URL_TV_MAZE + fakeModel.id)
    }

    @Test
    fun `should fill info about tv show`() {
        presenter.onAttachView(view)

        verify(view).setSummary(fakeModel.summary)
        verify(view).setEpisodeRuntime(fakeModel.episodeRuntime)
        verify(view).setGenre(fakeModel.genre)
    }

    @Test
    fun `should visible official site when is present`() {
        presenter.onAttachView(view)

        verify(view).setOfficialSiteVisible()
    }

    @Test
    fun `should set network when is present`() {
        presenter.onAttachView(view)

        verify(view).setNetwork(fakeModel.network)
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
