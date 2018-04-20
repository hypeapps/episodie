package pl.hypeapp.episodie.ui.viewmodel

import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyZeroInteractions
import org.amshove.kluent.`should equal to`
import org.amshove.kluent.`should equal`
import org.amshove.kluent.mock
import org.junit.Before
import org.junit.Test
import pl.hypeapp.domain.model.PageableRequest
import pl.hypeapp.domain.model.collections.MostPopularModel
import pl.hypeapp.domain.model.tvshow.TvShowModel

class MostPopularViewModelTest {

    lateinit var mostPopularViewModel: MostPopularViewModel

    private val mostPopularModel: MostPopularModel = mock()

    private val pageableRequest: PageableRequest = mock()

    @Before
    fun setUp() {
        mostPopularViewModel = MostPopularViewModel()
        given(mostPopularModel.pageableRequest).willReturn(pageableRequest)
    }

    @Test
    fun `should clear and retain model`() {
        mostPopularViewModel.clearAndRetainModel(mostPopularModel)

        mostPopularViewModel.retainedModel `should equal` mostPopularModel
        mostPopularViewModel.page `should equal` 0
        mostPopularViewModel.tvShowList `should equal` mostPopularModel.tvShows
    }

    @Test
    fun `should retain model`() {
        val model = createFakeModel()
        mostPopularViewModel.retainModel(model)

        mostPopularViewModel.retainedModel `should equal` model
        mostPopularViewModel.page `should equal to` 2
    }

    @Test
    fun `should populate recycler while model is not null`() {
        val model = createFakeModel()
        mostPopularViewModel.retainedModel = model
        val populateRecycler: () -> Unit = mock()
        val requestMostPopular: () -> Unit = mock()

        mostPopularViewModel.loadModel(requestMostPopular, populateRecycler)
        verify(populateRecycler).invoke()
        verifyZeroInteractions(requestMostPopular)
    }

    @Test
    fun `should request most popular while model is null`() {
        mostPopularViewModel.retainedModel = null
        val populateRecycler: () -> Unit = mock()
        val requestMostPopular: () -> Unit = mock()

        mostPopularViewModel.loadModel(requestMostPopular, populateRecycler)
        verify(requestMostPopular).invoke()
        verifyZeroInteractions(populateRecycler)
    }

    private fun createFakeModel(): MostPopularModel {
        val tvShowModel = MostPopularModel(arrayListOf(TvShowModel(
                id = "2",
                episodeRuntime = 200L,
                fullRuntime = 200L,
                imageMedium = "http://fake.jpg",
                imageOriginal = "http://fakeOriginal.jpg",
                imdbId = "tt2222",
                name = "example show",
                premiered = "2017-11-11",
                genre = "Drama",
                watchState = 0,
                summary = "",
                episodeOrder = 10,
                officialSite = "official site",
                network = "HBO",
                status = "running"
        )), PageableRequest(page = 2, size = 15))
        return tvShowModel
    }

}
