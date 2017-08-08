package pl.hypeapp.presentation.mostpopular

import pl.hypeapp.domain.model.MostPopularModel
import pl.hypeapp.domain.usecase.base.DefaultSingleObserver
import pl.hypeapp.domain.usecase.mostpopular.MostPopularUseCase
import pl.hypeapp.presentation.base.Presenter
import java.util.logging.Logger
import javax.inject.Inject

class MostPopularPresenter @Inject constructor(val useCase: MostPopularUseCase) : Presenter<MostPopularView>() {

    private companion object

    val SIZE = 15

    var isLastPage: Boolean = false

    override fun onAttachView(view: MostPopularView) {
        super.onAttachView(view)
        this.view?.initRecyclerAdapter()
        this.view?.initSwipeRefreshLayout()
        this.view?.loadViewModel()
    }

    override fun onDetachView() {
        super.onDetachView()
        useCase.dispose()
    }

    fun requestMostPopular(page: Int, update: Boolean) {
        if (!isLastPage)
            useCase.execute(MostPopularObserver(), MostPopularUseCase.Params.createQuery(page, SIZE, update))
    }

    fun onRefresh() {
        isLastPage = false
        requestMostPopular(0, true)
    }

    val log: Logger = Logger.getAnonymousLogger()

    inner class MostPopularObserver : DefaultSingleObserver<MostPopularModel>() {

        override fun onSuccess(model: MostPopularModel) {
            this@MostPopularPresenter.view?.populateRecyclerList(model)
            this@MostPopularPresenter.isLastPage = model.pageableRequest.last
        }

        override fun onError(error: Throwable) {
            log.info(error.printStackTrace().toString())
            this@MostPopularPresenter.view?.showError()
        }
    }


}
