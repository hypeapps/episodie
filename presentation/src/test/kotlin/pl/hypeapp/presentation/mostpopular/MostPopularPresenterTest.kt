package pl.hypeapp.presentation.mostpopular

import com.nhaarman.mockito_kotlin.*
import org.amshove.kluent.`should equal`
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import pl.hypeapp.domain.model.MostPopularModel
import pl.hypeapp.domain.model.PageableRequest
import pl.hypeapp.domain.usecase.mostpopular.MostPopularUseCase

class MostPopularPresenterTest {

    private lateinit var mostPopularPresenter: MostPopularPresenter

    private val mostPopularView: MostPopularView = mock()

    private val mostPopularUseCase: MostPopularUseCase = mock()

    companion object

    val PAGE = 6

    @Before
    fun setUp() {
        mostPopularPresenter = MostPopularPresenter(mostPopularUseCase)
    }

    @Test
    fun `should load view model`() {
        mostPopularPresenter.onAttachView(mostPopularView)
        verify(mostPopularView).loadViewModel()
    }

    @Test
    fun `should dispose use case`() {
        mostPopularPresenter.onDetachView()
        verify(mostPopularUseCase).dispose()
    }

    @Test
    fun `should execute use case`() {
        mostPopularPresenter.requestMostPopular(PAGE, false)
        verify(mostPopularUseCase).execute(any(), any())
    }

    @Test
    fun `should not execute use case when is last page`() {
        mostPopularPresenter.isLastPage = true
        mostPopularPresenter.requestMostPopular(PAGE, false)
        verifyZeroInteractions(mostPopularUseCase)
    }

    @Test
    fun `should on refresh call on first page`() {
        `when`(mostPopularUseCase.execute(any(), any())).then({
            (it.arguments[1] as MostPopularUseCase.Params).update `should equal` true
            (it.arguments[1] as MostPopularUseCase.Params).pageableRequest.page `should equal` 0
        })
        mostPopularPresenter.onRefresh()
        verify(mostPopularUseCase).execute(any(), any())
    }

    @Test
    fun `should pass model and populate recycler`() {
        mostPopularPresenter.view = mostPopularView
        val mostPopularModel: MostPopularModel = MostPopularModel(mock(), PageableRequest(last = false))


        `when`(mostPopularUseCase.execute(any(), any())).thenAnswer({
            (it.arguments[0] as MostPopularPresenter.MostPopularObserver).onSuccess(mostPopularModel)
        })

        mostPopularPresenter.requestMostPopular(4, true)

        verify(mostPopularView, times(1)).populateRecyclerList(mostPopularModel)
    }

    @Test
    fun `should show error`() {
        mostPopularPresenter.view = mostPopularView
        val throwable: Throwable = mock()

        `when`(mostPopularUseCase.execute(any(), any())).thenAnswer({
            (it.arguments[0] as MostPopularPresenter.MostPopularObserver).onError(throwable)
        })

        mostPopularPresenter.requestMostPopular(4, true)

        verify(mostPopularView, times(1)).showError()
    }

}
