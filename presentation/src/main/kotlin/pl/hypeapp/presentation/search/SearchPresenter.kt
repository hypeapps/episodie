package pl.hypeapp.presentation.search

import pl.hypeapp.domain.model.WatchState
import pl.hypeapp.domain.model.tvshow.TvShowModel
import pl.hypeapp.domain.usecase.base.DefaultCompletableObserver
import pl.hypeapp.domain.usecase.base.DefaultSingleObserver
import pl.hypeapp.domain.usecase.gettvshow.GetTvShowUseCase
import pl.hypeapp.domain.usecase.search.BasicSearchUseCase
import pl.hypeapp.domain.usecase.userstats.UserRuntimeUseCase
import pl.hypeapp.domain.usecase.watchstate.UpdateTvShowWatchStateByIdUseCase
import pl.hypeapp.domain.usecase.watchstate.mapwatched.MapTvShowsWatchStateUseCase
import pl.hypeapp.presentation.base.Presenter
import javax.inject.Inject

class SearchPresenter @Inject constructor(private val searchUseCase: BasicSearchUseCase,
                                          private val updateTvShowWatchStateById: UpdateTvShowWatchStateByIdUseCase,
                                          private val mapTvShowsWatchStateUseCase: MapTvShowsWatchStateUseCase,
                                          private val userRuntimeUseCase: UserRuntimeUseCase,
                                          private val getTvShowUseCase: GetTvShowUseCase) : Presenter<SearchView>() {

    var model: List<TvShowModel> = emptyList()

    var userRuntime: Long = 0

    override fun onAttachView(view: SearchView) {
        super.onAttachView(view)
        this.view?.setNavigationBarOptions()
        this.view?.initNavigationDrawer()
        this.view?.initSearchView()
        this.view?.initRecyclerAdapter()
        this.view?.loadViewModel()
        this.updateUserRuntime()
    }

    override fun onDetachView() {
        super.onDetachView()
        searchUseCase.dispose()
    }

    fun executeQuery(query: String) {
        searchUseCase.execute(SearchObserver(), BasicSearchUseCase.Params.createQuery(query))
    }

    fun onChangeWatchState(tvShowModel: TvShowModel) = with(tvShowModel) {
        val isAddToWatchedOperation: Boolean = watchState != WatchState.WATCHED
        watchState = WatchState.toggleWatchState(watchState)
        updateTvShowWatchStateById.execute(UpdateWatchStateObserver(),
                UpdateTvShowWatchStateByIdUseCase.Params.createParams(id!!, isAddToWatchedOperation))
    }

    fun onSearchQuerySubmit() {
        this.view?.dismissSearchView()
        if (model.isEmpty()) {
            this.view?.showErrorToast()
            return
        }
        this.view?.populateRecyclerWithSuggestions(model)
    }

    fun onItemClick(tvShowId: String) {
        getTvShowUseCase.execute(GetTvShowObserver(), GetTvShowUseCase.Params.createParams(tvShowId, false))
    }

    fun checkWatchStateIntegrity(tvShows: List<TvShowModel>) {
        mapTvShowsWatchStateUseCase.execute(UpdateSearchItemsObserver(), MapTvShowsWatchStateUseCase.Params.createParams(tvShows))
    }

    fun updateUserRuntime() = userRuntimeUseCase.execute(UserRuntimeObserver(), null)

    inner class GetTvShowObserver : DefaultSingleObserver<TvShowModel>() {
        override fun onSuccess(model: TvShowModel) {
            this@SearchPresenter.view?.navigateToTvShowDetails(model)
        }

        override fun onError(error: Throwable) {
            this@SearchPresenter.view?.showErrorToast()
        }
    }

    inner class SearchObserver : DefaultSingleObserver<List<TvShowModel>>() {
        override fun onSuccess(model: List<TvShowModel>) {
            if (!model.isEmpty())
                this@SearchPresenter.view?.setSearchViewSuggestions(model
                        .map { it.name!! }
                        .take(4)
                        .toTypedArray())
            this@SearchPresenter.model = model
        }

        override fun onError(error: Throwable) {
            this@SearchPresenter.model = emptyList()
        }
    }

    inner class UpdateWatchStateObserver : DefaultCompletableObserver() {
        override fun onComplete() {
            // Get updated fullRuntime and execute UserRuntimeAfterChangeObserver
            this@SearchPresenter.userRuntimeUseCase.execute(UserRuntimeAfterChangeObserver(), null)
        }

        override fun onError(e: Throwable) {
            this@SearchPresenter.view?.onChangeWatchStateError()
        }
    }

    inner class UpdateSearchItemsObserver : DefaultSingleObserver<List<TvShowModel>>() {
        override fun onSuccess(model: List<TvShowModel>) {
            this@SearchPresenter.model = model
            this@SearchPresenter.view?.populateRecyclerWithSuggestions(model)
        }
    }

    inner class UserRuntimeAfterChangeObserver : DefaultSingleObserver<Long>() {
        override fun onSuccess(model: Long) {
            this@SearchPresenter.view?.showRuntimeNotification(oldUserRuntime = userRuntime, newRuntime = model)
            this@SearchPresenter.userRuntime = model
        }

        override fun onError(error: Throwable) {
            super.onError(error)
            this@SearchPresenter.view?.showRuntimeNotification(oldUserRuntime = userRuntime, newRuntime = 0)
            this@SearchPresenter.userRuntime = 0
        }
    }

    inner class UserRuntimeObserver : DefaultSingleObserver<Long>() {
        override fun onSuccess(model: Long) {
            this@SearchPresenter.userRuntime = model
        }
    }


}
