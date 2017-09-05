package pl.hypeapp.presentation.toplist

import pl.hypeapp.domain.model.TopListModel
import pl.hypeapp.domain.model.TvShowModel
import pl.hypeapp.domain.model.WatchState
import pl.hypeapp.domain.usecase.base.DefaultCompletableObserver
import pl.hypeapp.domain.usecase.base.DefaultSingleObserver
import pl.hypeapp.domain.usecase.mapwatched.TvShowWatchStateIntegrityUseCase
import pl.hypeapp.domain.usecase.top.TopListUseCase
import pl.hypeapp.domain.usecase.watchstate.ManageTvShowWatchStateUseCase
import pl.hypeapp.presentation.base.Presenter
import java.util.logging.Logger
import javax.inject.Inject

class TopListPresenter @Inject constructor(private val topListUseCase: TopListUseCase,
                                           private val manageTvShowWatchStateUseCase: ManageTvShowWatchStateUseCase,
                                           private val tvShowWatchStateIntegrityUseCase: TvShowWatchStateIntegrityUseCase)
    : Presenter<TopListView>() {

    private companion object {
        val SIZE = 10
        val PAGE_LIMIT = 9
    }

    override fun onAttachView(view: TopListView) {
        super.onAttachView(view)
        this.view?.initSwipeRefreshLayout()
        this.view?.initRecyclerAdapter()
        this.view?.loadBackdrop()
        this.view?.observeDragDrawer()
        this.view?.observeActivityReenter()
        this.view?.loadViewModel()
    }

    override fun onDetachView() {
        super.onDetachView()
        topListUseCase.dispose()
        tvShowWatchStateIntegrityUseCase.dispose()
        manageTvShowWatchStateUseCase.dispose()
    }

    fun onDrawerDrag(dragProgress: Float) {
        this.view?.animateDrawerHamburgerArrow(dragProgress)
    }

    fun requestTopList(page: Int, update: Boolean) {
        if (page <= PAGE_LIMIT)
            topListUseCase.execute(TopListObserver(), TopListUseCase.Params.createQuery(page, SIZE, update))
    }

    fun updateModel(tvShows: List<TvShowModel>) {
        tvShowWatchStateIntegrityUseCase.execute(UpdateTopListObserver(),
                TvShowWatchStateIntegrityUseCase.Params.createParams(tvShows))
    }

    fun changeWatchedState(tvShow: TvShowModel) = with(tvShow) {
        val addToWatched: Boolean = watchState != WatchState.WATCHED
        watchState = WatchState.manageWatchState(watchState)
        manageTvShowWatchStateUseCase.execute(ManageTvShowWatchStateObserver(),
                ManageTvShowWatchStateUseCase.Params.createParams(id!!, addToWatched))
    }

    inner class TopListObserver : DefaultSingleObserver<TopListModel>() {
        override fun onSuccess(model: TopListModel) {
            this@TopListPresenter.view?.populateRecyclerList(model)
        }

        override fun onError(error: Throwable) {
            this@TopListPresenter.view?.showError()
        }
    }

    val log: Logger = Logger.getAnonymousLogger()

    inner class UpdateTopListObserver : DefaultSingleObserver<List<TvShowModel>>() {
        override fun onSuccess(model: List<TvShowModel>) {
            this@TopListPresenter.view?.updateRecyclerList(model)
        }
    }

    inner class ManageTvShowWatchStateObserver : DefaultCompletableObserver() {
        override fun onComplete() {

        }

        override fun onError(e: Throwable) {
            this@TopListPresenter.view?.onChangeWatchStateError()
        }
    }

}
