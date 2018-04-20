package pl.hypeapp.presentation.seasontracker

import pl.hypeapp.domain.model.collections.SeasonTrackerModel
import pl.hypeapp.domain.model.tvshow.SeasonModel
import pl.hypeapp.domain.model.tvshow.TvShowExtendedModel
import pl.hypeapp.domain.model.tvshow.TvShowModel
import pl.hypeapp.domain.usecase.allepisodes.AllEpisodesUseCase
import pl.hypeapp.domain.usecase.base.DefaultCompletableObserver
import pl.hypeapp.domain.usecase.base.DefaultSingleObserver
import pl.hypeapp.domain.usecase.search.BasicSearchUseCase
import pl.hypeapp.domain.usecase.seasontracker.DeleteSeasonTrackerUseCase
import pl.hypeapp.domain.usecase.seasontracker.SeasonTrackerUseCase
import pl.hypeapp.domain.usecase.seasontracker.UpdateSeasonTrackerUseCase
import pl.hypeapp.presentation.base.Presenter
import java.util.logging.Logger
import javax.inject.Inject

class SeasonTrackerPresenter @Inject constructor(private val searchUseCase: BasicSearchUseCase,
                                                 private val getAllEpisodesUseCase: AllEpisodesUseCase,
                                                 private val updateSeasonTrackerUseCase: UpdateSeasonTrackerUseCase,
                                                 private val seasonTrackerUseCase: SeasonTrackerUseCase,
                                                 private val deleteSeasonTrackerUseCase: DeleteSeasonTrackerUseCase)
    : Presenter<SeasonTrackerView>() {

    var model: List<TvShowModel> = emptyList()

    var isSeasonTrackerStarted = false

    override fun onAttachView(view: SeasonTrackerView) {
        super.onAttachView(view)
        this.view?.initNavigationDrawer()
        this.view?.showLoading()
        if (this.view?.getModel() != null) {
            this.view?.initSeasonTracker()
            this.view?.loadTvShowHeader()
            this.view?.hideSearch()
            this.view?.hideLoading()
        } else {
            seasonTrackerUseCase.execute(SeasonTrackerObserver(), null)
        }
    }

    override fun onDetachView() {
        super.onDetachView()
        searchUseCase.dispose()
        getAllEpisodesUseCase.dispose()
        updateSeasonTrackerUseCase.dispose()
        seasonTrackerUseCase.dispose()
        deleteSeasonTrackerUseCase.dispose()
    }

    fun executeQuery(query: String) {
        searchUseCase.execute(SearchObserver(), BasicSearchUseCase.Params.createQuery(query))
    }

    fun onSearchQuerySubmit(query: String?) {
        view?.dismissSearchView()
        if (model.isEmpty()) {
            this.view?.showErrorToast()
            return
        }
        val filteredModel: TvShowModel? = model.firstOrNull { it.name.equals(query) }
        if (filteredModel != null) {
            getAllSeasons(filteredModel.id)
        } else {
            this.view?.showSuggestionsDialog(model.map { it.name }.toTypedArray())
        }
    }

    fun onSeasonSelected(seasonModel: SeasonModel) {
        this.view?.retainViewModel(SeasonTrackerModel(seasonModel.tvShowId!!, seasonModel.seasonId!!, arrayListOf()))
        updateSeasonTrackerUseCase.execute(UpdateBingeWatchingObserver(),
                UpdateSeasonTrackerUseCase.Params.createQuery(this.view?.getModel()!!))
        this.view?.showLoading()
        isSeasonTrackerStarted = true
        getAllSeasons(seasonModel.tvShowId)
    }

    fun onRestartSeasonTracker() {
        showSearchComponent()
        this.view?.cancelNotifications()
        deleteSeasonTrackerUseCase.execute(UpdateBingeWatchingObserver(), null)
    }

    fun updateSeasonTracker(seasonTrackerModel: SeasonTrackerModel?) {
        updateSeasonTrackerUseCase.execute(UpdateBingeWatchingObserver(),
                UpdateSeasonTrackerUseCase.Params.createQuery(seasonTrackerModel!!))
        this.view?.updateRecyclerView()
    }

    fun onRetry() {
        seasonTrackerUseCase.execute(SeasonTrackerObserver(), null)
        this.view?.hideErrorView()
    }

    private fun getAllSeasons(tvShowId: String?) = tvShowId?.let {
        getAllEpisodesUseCase.execute(AllSeasonsObserver(),
                AllEpisodesUseCase.Params.createQuery(it, true, false))
    }

    private fun showSearchComponent() {
        isSeasonTrackerStarted = false
        this.view?.showSearchComponent()
        this.view?.initSearchView()
        this.view?.hideLoading()
        this.view?.hideErrorView()
    }

    private fun startBingeWatchingTracker(tvShowExtended: TvShowExtendedModel) {
        this.view?.hideLoading()
        val season = tvShowExtended.seasons!!.first { it.seasonId.equals(this.view?.getModel()?.seasonId) }
        this.view?.retainSeasonViewModel(season, tvShowExtended)
        this.view?.initSeasonTracker()
        this.view?.scheduleNotifications()
        this.view?.loadTvShowHeader()
        this.view?.hideSearch()
    }

    inner class SearchObserver : DefaultSingleObserver<List<TvShowModel>>() {
        override fun onSuccess(model: List<TvShowModel>) {
            if (!model.isEmpty())
                this@SeasonTrackerPresenter.view?.setSearchSuggestions(model
                        .map { it.name!! }
                        .take(3)
                        .toTypedArray())
            this@SeasonTrackerPresenter.model = model
        }

        override fun onError(error: Throwable) {
            this@SeasonTrackerPresenter.model = emptyList()
        }
    }

    inner class AllSeasonsObserver : DefaultSingleObserver<TvShowExtendedModel>() {
        override fun onSuccess(model: TvShowExtendedModel) {
            if (isSeasonTrackerStarted) {
                startBingeWatchingTracker(model)
            } else {
                this@SeasonTrackerPresenter.view?.showSelectSeasonDialog(model)
            }
            this@SeasonTrackerPresenter.view?.hideErrorView()
        }

        override fun onError(error: Throwable) {
            isSeasonTrackerStarted = false
            this@SeasonTrackerPresenter.view?.clearModel()
            this@SeasonTrackerPresenter.view?.showErrorView()
            this@SeasonTrackerPresenter.view?.hideLoading()
        }
    }

    val log = Logger.getAnonymousLogger()

    inner class SeasonTrackerObserver : DefaultSingleObserver<SeasonTrackerModel>() {
        override fun onSuccess(model: SeasonTrackerModel) {
            isSeasonTrackerStarted = true
            view?.retainViewModel(model)
            getAllEpisodesUseCase.execute(AllSeasonsObserver(),
                    AllEpisodesUseCase.Params.createQuery(model.tvShowId, true, false))
        }

        override fun onError(error: Throwable) {
            log.info(error.message)
            showSearchComponent()
        }
    }

    private inner class UpdateBingeWatchingObserver : DefaultCompletableObserver()

    fun onDisableNotifications() {

    }

}
