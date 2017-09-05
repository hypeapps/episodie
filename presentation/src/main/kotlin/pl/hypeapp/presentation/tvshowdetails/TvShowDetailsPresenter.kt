package pl.hypeapp.presentation.tvshowdetails

import pl.hypeapp.domain.model.TvShowModel
import pl.hypeapp.domain.model.WatchState
import pl.hypeapp.domain.usecase.base.DefaultCompletableObserver
import pl.hypeapp.domain.usecase.base.DefaultSingleObserver
import pl.hypeapp.domain.usecase.mapwatched.TvShowWatchStateIntegrityUseCase
import pl.hypeapp.domain.usecase.watchstate.ManageTvShowWatchStateUseCase
import pl.hypeapp.presentation.base.Presenter
import java.util.logging.Logger
import javax.inject.Inject

class TvShowDetailsPresenter @Inject constructor(private val manageTvShowWatchStateUseCase: ManageTvShowWatchStateUseCase,
                                                 private val tvShowWatchStateIntegrityUseCase: TvShowWatchStateIntegrityUseCase)
    : Presenter<TvShowDetailsView>() {

    override fun onAttachView(view: TvShowDetailsView) {
        super.onAttachView(view)
        val model = this.view?.model
        this.view?.setNavigationBarOptions()
        this.view?.initPagerAdapter(model)
        updateWatchState()
        filTvShowDetailsInfo(model)
    }

    override fun onDetachView() {
        super.onDetachView()
        manageTvShowWatchStateUseCase.dispose()
        tvShowWatchStateIntegrityUseCase.dispose()
    }

    fun onPageSelected(position: Int) {
        if (position <= 0) {
            view?.expandAppBar()
        }
    }

    fun onPagerAdapterInit() {
        view?.startFabButtonAnimation()
    }

    fun onChangeWatchedTvShowState() = with(this.view?.model!!) {
        val addToWatched: Boolean = watchState != WatchState.WATCHED
        watchState = WatchState.manageWatchState(watchState)
        manageTvShowWatchStateUseCase.execute(ManageTvShowWatchStateObserver(),
                ManageTvShowWatchStateUseCase.Params.createParams(id!!, addToWatched))
    }

    fun updateWatchState() = with(this.view?.model!!) {
        tvShowWatchStateIntegrityUseCase.execute(TvShowWatchStateIntegrityObserver(),
                TvShowWatchStateIntegrityUseCase.Params.createParams(listOf(this)))
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

    val logger: Logger = Logger.getLogger(this::class.java.toString())

    inner class ManageTvShowWatchStateObserver : DefaultCompletableObserver() {
        override fun onComplete() {
            this@TvShowDetailsPresenter.view?.onChangedWatchState()
        }

        override fun onError(e: Throwable) {
            this@TvShowDetailsPresenter.updateWatchState()
            this@TvShowDetailsPresenter.view?.onChangeWatchStateError()
        }
    }

    inner class TvShowWatchStateIntegrityObserver : DefaultSingleObserver<List<TvShowModel>>() {
        override fun onSuccess(model: List<TvShowModel>) {
            this@TvShowDetailsPresenter.view?.model?.watchState = model.first().watchState
            this@TvShowDetailsPresenter.view?.updateWatchState(model.first().watchState)
        }
    }

}
