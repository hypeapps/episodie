package pl.hypeapp.presentation.seasons

import pl.hypeapp.domain.model.WatchState
import pl.hypeapp.domain.model.tvshow.EpisodeModel
import pl.hypeapp.domain.model.tvshow.SeasonModel
import pl.hypeapp.domain.model.tvshow.TvShowExtendedModel
import pl.hypeapp.domain.usecase.base.DefaultCompletableObserver
import pl.hypeapp.domain.usecase.base.DefaultSingleObserver
import pl.hypeapp.domain.usecase.gettvshow.GetTvShowExtendedUseCase
import pl.hypeapp.domain.usecase.watchstate.UpdateEpisodeWatchStateUseCase
import pl.hypeapp.domain.usecase.watchstate.UpdateSeasonWatchStateUseCase
import pl.hypeapp.domain.usecase.watchstate.mapwatched.MapTvShowWatchStateUseCase
import pl.hypeapp.presentation.base.Presenter
import javax.inject.Inject

class SeasonsPresenter @Inject constructor(private val getTvShowExtendedUseCase: GetTvShowExtendedUseCase,
                                           private val updateEpisodeWatchStateUseCase: UpdateEpisodeWatchStateUseCase,
                                           private val updateSeasonWatchStateUseCase: UpdateSeasonWatchStateUseCase,
                                           private val mapWatchStateUseCase: MapTvShowWatchStateUseCase)
    : Presenter<SeasonsView>() {

    private var isViewShown = false

    override fun onAttachView(view: SeasonsView) {
        super.onAttachView(view)
        this.view?.showLoading()
    }

    override fun onDetachView() {
        super.onDetachView()
        getTvShowExtendedUseCase.dispose()
        updateEpisodeWatchStateUseCase.dispose()
        updateSeasonWatchStateUseCase.dispose()
        mapWatchStateUseCase.dispose()
    }

    // For performance reason load a list when a fragment is visible for first time.
    fun onViewShown() {
        if (!isViewShown) {
            isViewShown = true
            this.view?.initRecyclerAdapter()
            this.view?.initSwipeToRefresh()
            this.view?.observeWatchStateInParentActivity()
            this.view?.loadViewModel()
        }
    }

    fun requestAllSeasons(tvShowId: String, update: Boolean) {
        this.view?.showLoading()
        this.view?.startLoadingAnimation()
        getTvShowExtendedUseCase.execute(SeasonsObserver(), GetTvShowExtendedUseCase.Params.createQuery(tvShowId, update))
    }

    fun toggleEpisodeWatchState(episodeModel: EpisodeModel) = with(episodeModel) {
        val addToWatched: Boolean = watchState != WatchState.WATCHED
        watchState = WatchState.toggleWatchState(watchState)
        updateEpisodeWatchStateUseCase.execute(UpdateWatchStateObserver(), UpdateEpisodeWatchStateUseCase.Params.createParams(this, addToWatched))
    }

    fun toggleSeasonWatchState(seasonModel: SeasonModel) = with(seasonModel) {
        val addToWatched: Boolean = watchState != WatchState.WATCHED
        watchState = WatchState.toggleWatchState(watchState)
        updateSeasonWatchStateUseCase.execute(UpdateWatchStateObserver(), UpdateSeasonWatchStateUseCase.Params.createParams(this, addToWatched))
    }

    fun checkWatchStateIntegrity(tvShowExtendedModel: TvShowExtendedModel?) {
        if (isViewShown) {
            mapWatchStateUseCase.execute(UpdateSeasonsObserver(), MapTvShowWatchStateUseCase.Params.createParams(tvShowExtendedModel!!))
        }
    }

    inner class SeasonsObserver : DefaultSingleObserver<TvShowExtendedModel>() {
        override fun onSuccess(model: TvShowExtendedModel) {
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

    inner class UpdateWatchStateObserver : DefaultCompletableObserver() {
        override fun onComplete() {
            this@SeasonsPresenter.view?.onChangedWatchState()
        }

        override fun onError(e: Throwable) {
            this@SeasonsPresenter.view?.onChangeWatchStateError()
        }
    }

    inner class UpdateSeasonsObserver : DefaultSingleObserver<TvShowExtendedModel>() {
        override fun onSuccess(model: TvShowExtendedModel) {
            this@SeasonsPresenter.view?.updateRecyclerList(model)
        }
    }

}
