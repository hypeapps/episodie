package pl.hypeapp.presentation.seasons

import pl.hypeapp.domain.model.AllSeasonsModel
import pl.hypeapp.domain.usecase.base.DefaultSingleObserver
import pl.hypeapp.domain.usecase.seasons.SeasonsUseCase
import pl.hypeapp.presentation.base.Presenter
import javax.inject.Inject

class SeasonsPresenter @Inject constructor(val useCase: SeasonsUseCase) : Presenter<SeasonsView>() {

    var isViewShown = false

    override fun onAttachView(view: SeasonsView) {
        super.onAttachView(view)
        this.view?.initRecyclerAdapter()
        this.view?.initSwipeToRefresh()
        this.view?.showLoading()
    }

    override fun onDetachView() {
        super.onDetachView()
        useCase.dispose()
    }

    // For performance reason load list when fragment is visible for first time.
    fun onViewShown() {
        if (!isViewShown) {
            isViewShown = true
            this.view?.loadViewModel()
        }
    }

    fun requestAllSeasons(tvShowId: String, update: Boolean) {
        this.view?.showLoading()
        useCase.execute(SeasonsObserver(), SeasonsUseCase.Params.createQuery(tvShowId, update))
    }

    inner class SeasonsObserver : DefaultSingleObserver<AllSeasonsModel>() {

        override fun onSuccess(model: AllSeasonsModel) {
            if (model.seasons?.size != 0) {
                this@SeasonsPresenter.view?.populateRecyclerView(model)
            } else {
                this@SeasonsPresenter.view?.showEmptySeasonsMessage()
            }
        }

        override fun onError(error: Throwable) {
            this@SeasonsPresenter.view?.showError()
        }

    }

}
