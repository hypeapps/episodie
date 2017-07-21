package pl.hypeapp.presentation.toplist

import pl.hypeapp.domain.model.TopListModel
import pl.hypeapp.domain.usecase.base.DefaultSingleObserver
import pl.hypeapp.domain.usecase.top.TopListUseCase
import pl.hypeapp.presentation.base.Presenter
import javax.inject.Inject

class TopListPresenter @Inject constructor(val topListUseCase: TopListUseCase) : Presenter<TopListView>() {

    private companion object {
        val SIZE = 10
        val MAX_PAGE = 9
    }

    override fun onAttachView(view: TopListView) {
        super.onAttachView(view)
        view.loadViewModel()
    }

    override fun onDetachView() {
        super.onDetachView()
        topListUseCase.dispose()
    }

    fun requestTopList(page: Int, update: Boolean) {
        if (page <= MAX_PAGE)
            topListUseCase.execute(TopListObserver(), TopListUseCase.Params.createQuery(page, SIZE, update))
    }

    inner class TopListObserver : DefaultSingleObserver<TopListModel>() {

        override fun onSuccess(model: TopListModel) {
            this@TopListPresenter.view?.populateRecyclerList(model)
        }

        override fun onError(error: Throwable) {
            this@TopListPresenter.view?.showError()
        }
    }
}
