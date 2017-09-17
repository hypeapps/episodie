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
import pl.hypeapp.domain.model.TopListModel
import pl.hypeapp.domain.model.TvShowModel

class TopListViewModelTest {

    lateinit var topListViewModel: TopListViewModel

    private val topListModel: TopListModel = mock()

    private val pageableRequest: PageableRequest = mock()

    @Before
    fun setUp() {
        topListViewModel = TopListViewModel()
        given(topListModel.pageableRequest).willReturn(pageableRequest)
    }

    @Test
    fun `should clear and retain model`() {
        topListViewModel.clearAndRetainModel(topListModel)

        topListViewModel.retainedModel `should equal` topListModel
        topListViewModel.page `should equal` 0
        topListViewModel.tvShowList `should equal` topListModel.tvShows
    }

    @Test
    fun `should retain model`() {
        val model = createFakeModel()
        topListViewModel.retainModel(model)

        topListViewModel.retainedModel `should equal` model
        topListViewModel.page `should equal to` 2
    }

    @Test
    fun `should populate recycler while model is not null`() {
        val model = createFakeModel()
        topListViewModel.retainedModel = model
        val populateRecycler: () -> Unit = mock()
        val requestMostPopular: () -> Unit = mock()

        topListViewModel.loadModel(requestMostPopular, populateRecycler)
        verify(populateRecycler).invoke()
        verifyZeroInteractions(requestMostPopular)
    }

    @Test
    fun `should request most popular while model is null`() {
        topListViewModel.retainedModel = null
        val populateRecycler: () -> Unit = mock()
        val requestMostPopular: () -> Unit = mock()

        topListViewModel.loadModel(requestMostPopular, populateRecycler)
        verify(requestMostPopular).invoke()
        verifyZeroInteractions(populateRecycler)
    }

    private fun createFakeModel(): TopListModel {
        val tvShowModel = TopListModel(arrayListOf(TvShowModel(
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
