package pl.hypeapp.presentation.mostpopular

import com.nhaarman.mockito_kotlin.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import pl.hypeapp.domain.model.MostPopularModel
import pl.hypeapp.domain.usecase.mostpopular.MostPopularUseCase

class MostPopularPresenterTest {

    private lateinit var mostPopularPresenter: MostPopularPresenter

    private val mostPopularView: MostPopularView = mock()

    private val mostPopularUseCase: MostPopularUseCase = mock()

    companion object {
        val PAGE = 6
        val PAGE_OVER_MAX = 7
    }

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
    fun `should not execute use case while page is over max`() {
        mostPopularPresenter.requestMostPopular(PAGE_OVER_MAX, false)
        verifyZeroInteractions(mostPopularUseCase)
    }

    @Test
    fun `should pass model and populate recycler`() {
        mostPopularPresenter.view = mostPopularView
        val mostPopularModel: MostPopularModel = mock()

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
