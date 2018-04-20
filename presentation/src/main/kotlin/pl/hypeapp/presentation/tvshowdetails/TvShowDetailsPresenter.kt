package pl.hypeapp.presentation.tvshowdetails

import pl.hypeapp.domain.model.WatchState
import pl.hypeapp.domain.model.tvshow.TvShowModel
import pl.hypeapp.domain.usecase.base.DefaultCompletableObserver
import pl.hypeapp.domain.usecase.base.DefaultSingleObserver
import pl.hypeapp.domain.usecase.userstats.UserRuntimeUseCase
import pl.hypeapp.domain.usecase.watchstate.UpdateTvShowWatchStateByIdUseCase
import pl.hypeapp.domain.usecase.watchstate.mapwatched.MapTvShowsWatchStateUseCase
import pl.hypeapp.presentation.base.Presenter
import javax.inject.Inject

class TvShowDetailsPresenter @Inject constructor(private val updateTvShowWatchStateByIdUseCase: UpdateTvShowWatchStateByIdUseCase,
                                                 private val mapTvShowsWatchStateUseCase: MapTvShowsWatchStateUseCase,
                                                 private val userRuntimeUseCase: UserRuntimeUseCase)
    : Presenter<TvShowDetailsView>() {

    private var userRuntime: Long = 0

    override fun onAttachView(view: TvShowDetailsView) {
        super.onAttachView(view)
        val model = this.view?.model
        this.view?.setNavigationBarOptions()
        updateWatchState()
        filTvShowDetailsInfo(model)
        this.view?.initPagerAdapter(model)
        updateUserRuntime()
    }

    override fun onDetachView() {
        updateTvShowWatchStateByIdUseCase.dispose()
        mapTvShowsWatchStateUseCase.dispose()
        userRuntimeUseCase.dispose()
    }

    fun onPageSelected(position: Int) {
        if (position <= 0) {
            view?.expandAppBar()
        }
    }

    fun onPagerAdapterInit() = view?.startFabButtonAnimation()

    fun onChangeWatchedTvShowState() = with(this.view?.model!!) {
        val idAddToWatchedOperation: Boolean = watchState != WatchState.WATCHED
        watchState = WatchState.toggleWatchState(watchState)
        updateTvShowWatchStateByIdUseCase.execute(ManageTvShowWatchStateObserver(),
                UpdateTvShowWatchStateByIdUseCase.Params.createParams(id!!, idAddToWatchedOperation))
    }

    fun updateWatchState() = with(this.view?.model!!) {
        mapTvShowsWatchStateUseCase.execute(TvShowWatchStateIntegrityObserver(),
                MapTvShowsWatchStateUseCase.Params.createParams(listOf(this)))
    }

    fun showRuntimeNotification() {
        // Get updated fullRuntime and execute UserRuntimeAfterChangeObserver
        userRuntimeUseCase.execute(UserRuntimeAfterChangeObserver(), null)
    }

    private fun updateUserRuntime() {
        userRuntimeUseCase.execute(UserRuntimeObserver(), null)
    }

    private fun filTvShowDetailsInfo(tvShowModel: TvShowModel?) {
        this.view?.setCover(tvShowModel?.imageMedium)
        this.view?.setBackdrop(backdropUrl = tvShowModel?.imageOriginal, placeholderUrl = tvShowModel?.imageMedium)
        this.view?.setTitle(tvShowModel?.name)
        this.view?.setFullRuntime(tvShowModel?.fullRuntime)
        this.view?.setPremiered(tvShowModel?.premiered)
        this.view?.setStatus(tvShowModel?.status)
        if (tvShowModel?.episodeOrder == 0) {
            this.view?.hideFabButton()
        }
    }

    inner class ManageTvShowWatchStateObserver : DefaultCompletableObserver() {
        override fun onComplete() {
            this@TvShowDetailsPresenter.view?.onWatchStateChanged()
            this@TvShowDetailsPresenter.showRuntimeNotification()
        }

        override fun onError(e: Throwable) {
            this@TvShowDetailsPresenter.updateWatchState()
            this@TvShowDetailsPresenter.view?.onWatchStateChangeError()
        }
    }

    inner class TvShowWatchStateIntegrityObserver : DefaultSingleObserver<List<TvShowModel>>() {
        override fun onSuccess(model: List<TvShowModel>) {
            this@TvShowDetailsPresenter.view?.model?.watchState = model.first().watchState
            this@TvShowDetailsPresenter.view?.updateWatchState(model.first().watchState)
        }
    }

    inner class UserRuntimeAfterChangeObserver : DefaultSingleObserver<Long>() {
        override fun onSuccess(model: Long) {
            this@TvShowDetailsPresenter.view?.showRuntimeNotification(oldUserRuntime = userRuntime, newRuntime = model)
            this@TvShowDetailsPresenter.userRuntime = model
        }

        override fun onError(error: Throwable) {
            this@TvShowDetailsPresenter.view?.showRuntimeNotification(oldUserRuntime = userRuntime, newRuntime = 0)
            this@TvShowDetailsPresenter.userRuntime = 0
        }
    }

    inner class UserRuntimeObserver : DefaultSingleObserver<Long>() {
        override fun onSuccess(model: Long) {
            this@TvShowDetailsPresenter.userRuntime = model
        }
    }

}
