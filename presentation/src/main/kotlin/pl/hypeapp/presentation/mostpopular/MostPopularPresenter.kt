package pl.hypeapp.presentation.mostpopular

import pl.hypeapp.domain.model.MostPopularModel
import pl.hypeapp.domain.usecase.base.DefaultSingleObserver
import pl.hypeapp.domain.usecase.mostpopular.MostPopularUseCase
import pl.hypeapp.presentation.base.Presenter
import javax.inject.Inject

class MostPopularPresenter @Inject constructor(val mostPopularUseCase: MostPopularUseCase) : Presenter<MostPopularView>() {

    private companion object {
        val SIZE = 15
        val MAX_PAGE = 6
    }

    override fun onAttachView(view: MostPopularView) {
        super.onAttachView(view)
        view.loadViewModel()
    }

    override fun onDetachView() {
        super.onDetachView()
        mostPopularUseCase.dispose()
    }

    fun requestMostPopular(page: Int, update: Boolean) {
        if (page <= MAX_PAGE)
            mostPopularUseCase.execute(MostPopularObserver(), MostPopularUseCase.Params.createQuery(page, SIZE, update))
    }

    inner class MostPopularObserver : DefaultSingleObserver<MostPopularModel>() {

        override fun onSuccess(t: MostPopularModel) {
            this@MostPopularPresenter.view?.populateRecyclerList(t)
        }

        override fun onError(e: Throwable) {
            this@MostPopularPresenter.view?.showError()
        }
    }

}
