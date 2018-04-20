package pl.hypeapp.presentation.toplist

import pl.hypeapp.domain.model.WatchState
import pl.hypeapp.domain.model.collections.TopListModel
import pl.hypeapp.domain.model.tvshow.TvShowModel
import pl.hypeapp.domain.usecase.base.DefaultCompletableObserver
import pl.hypeapp.domain.usecase.base.DefaultSingleObserver
import pl.hypeapp.domain.usecase.top.TopListUseCase
import pl.hypeapp.domain.usecase.userstats.UserRuntimeUseCase
import pl.hypeapp.domain.usecase.watchstate.UpdateTvShowWatchStateByIdUseCase
import pl.hypeapp.domain.usecase.watchstate.mapwatched.MapTvShowsWatchStateUseCase
import pl.hypeapp.presentation.base.Presenter
import javax.inject.Inject

class TopListPresenter @Inject constructor(private val topListUseCase: TopListUseCase,
                                           private val updateTvShowWatchStateByIdUseCase: UpdateTvShowWatchStateByIdUseCase,
                                           private val mapTvShowsWatchStateUseCase: MapTvShowsWatchStateUseCase,
                                           private val userRuntimeUseCase: UserRuntimeUseCase)
    : Presenter<TopListView>() {

    private var userRuntime: Long = 0

    private companion object {
        const val SIZE = 10
        const val PAGE_LIMIT = 9
    }

    override fun onAttachView(view: TopListView) {
        super.onAttachView(view)
        this.view?.initSwipeRefreshLayout()
        this.view?.initRecyclerAdapter()
        this.view?.loadBackdrop()
        this.view?.observeDragDrawer()
        this.view?.observeActivityReenter()
        this.view?.loadViewModel()
        this.updateUserRuntime()
    }

    override fun onDetachView() {
        super.onDetachView()
        topListUseCase.dispose()
        mapTvShowsWatchStateUseCase.dispose()
        updateTvShowWatchStateByIdUseCase.dispose()
        userRuntimeUseCase.dispose()
    }

    fun onDrawerDrag(dragProgress: Float) = this.view?.animateDrawerHamburgerArrow(dragProgress)

    fun requestTopList(page: Int, update: Boolean) {
        if (page <= PAGE_LIMIT)
            topListUseCase.execute(TopListObserver(), TopListUseCase.Params.createQuery(page, SIZE, update))
    }

    fun checkWatchStateIntegrity(tvShows: List<TvShowModel>) {
        mapTvShowsWatchStateUseCase.execute(UpdateTopListObserver(), MapTvShowsWatchStateUseCase.Params.createParams(tvShows))
    }

    fun toggleWatchState(tvShow: TvShowModel) = with(tvShow) {
        val isAddToWatchedOperation: Boolean = watchState != WatchState.WATCHED
        watchState = WatchState.toggleWatchState(watchState)
        updateTvShowWatchStateByIdUseCase.execute(UpdateWatchStateObserver(),
                UpdateTvShowWatchStateByIdUseCase.Params.createParams(id!!, isAddToWatchedOperation))
    }

    fun updateUserRuntime() = userRuntimeUseCase.execute(UserRuntimeObserver(), null)

    inner class TopListObserver : DefaultSingleObserver<TopListModel>() {
        override fun onSuccess(model: TopListModel) {
            this@TopListPresenter.view?.populateRecyclerList(model)
        }

        override fun onError(error: Throwable) {
            this@TopListPresenter.view?.showError()
        }
    }

    inner class UpdateTopListObserver : DefaultSingleObserver<List<TvShowModel>>() {
        override fun onSuccess(model: List<TvShowModel>) {
            this@TopListPresenter.view?.updateRecyclerList(model)
        }
    }

    inner class UpdateWatchStateObserver : DefaultCompletableObserver() {
        override fun onComplete() {
            // Get updated fullRuntime and execute UserRuntimeAfterChangeObserver
            this@TopListPresenter.userRuntimeUseCase.execute(UserRuntimeAfterChangeObserver(), null)
        }

        override fun onError(e: Throwable) {
            this@TopListPresenter.view?.onChangeWatchStateError()
        }
    }

    inner class UserRuntimeAfterChangeObserver : DefaultSingleObserver<Long>() {
        override fun onSuccess(model: Long) {
            this@TopListPresenter.view?.showRuntimeNotification(oldUserRuntime = userRuntime, newRuntime = model)
            this@TopListPresenter.userRuntime = model
        }

        override fun onError(error: Throwable) {
            super.onError(error)
            this@TopListPresenter.view?.showRuntimeNotification(oldUserRuntime = userRuntime, newRuntime = 0)
            this@TopListPresenter.userRuntime = 0
        }
    }

    inner class UserRuntimeObserver : DefaultSingleObserver<Long>() {
        override fun onSuccess(model: Long) {
            this@TopListPresenter.userRuntime = model
        }
    }

}
