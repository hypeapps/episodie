package pl.hypeapp.presentation.yourlibrary

import pl.hypeapp.domain.model.Pageable
import pl.hypeapp.domain.model.tvshow.TvShowModel
import pl.hypeapp.domain.model.watched.UserStatsModel
import pl.hypeapp.domain.model.watched.WatchedTvShowModel
import pl.hypeapp.domain.usecase.base.DefaultCompletableObserver
import pl.hypeapp.domain.usecase.base.DefaultSingleObserver
import pl.hypeapp.domain.usecase.userstats.UserRuntimeUseCase
import pl.hypeapp.domain.usecase.userstats.UserStatsUseCase
import pl.hypeapp.domain.usecase.watchstate.UpdateTvShowWatchStateByIdUseCase
import pl.hypeapp.domain.usecase.yourlibrary.GetWatchedTvShowUseCase
import pl.hypeapp.presentation.base.Presenter
import javax.inject.Inject

class YourLibraryPresenter @Inject constructor(private val getWatchedTvShowUseCase: GetWatchedTvShowUseCase,
                                               private val updateTvShowWatchStateById: UpdateTvShowWatchStateByIdUseCase,
                                               private val getUserStatsUseCase: UserStatsUseCase,
                                               private val userRuntimeUseCase: UserRuntimeUseCase)
    : Presenter<YourLibraryView>() {

    companion object {
        const val PAGE_SIZE = 10
    }

    private var userRuntime: Long = 0

    override fun onAttachView(view: YourLibraryView) {
        super.onAttachView(view)
        this.view?.initNavigationDrawer()
        this.view?.initRecyclerAdapter()
        this.view?.loadViewModel()
        requestUserStats()
        updateUserRuntime()
    }

    override fun onDetachView() {
        super.onDetachView()
        getWatchedTvShowUseCase.dispose()
        updateTvShowWatchStateById.dispose()
        getUserStatsUseCase.dispose()
        userRuntimeUseCase.dispose()
    }

    fun requestModel(page: Int, update: Boolean) {
        view?.let {
            if (!it.isLastPage) {
                getWatchedTvShowUseCase.execute(WatchedTvShowObserver(), GetWatchedTvShowUseCase.Params
                        .createQuery(page, PAGE_SIZE, update))
            }
        }
    }

    fun updateUserRuntime() = userRuntimeUseCase.execute(UserRuntimeObserver(), null)

    fun requestUserStats() = getUserStatsUseCase.execute(UserStatsObserver(), null)

    fun onSwiped(adapterPosition: Int?) {
        adapterPosition?.let { this.view?.deleteRecyclerItemAt(adapterPosition) }
    }

    fun onItemRemoved(tvShow: TvShowModel?) {
        tvShow?.let {
            updateTvShowWatchStateById.execute(UpdateWatchStateObserver(),
                    UpdateTvShowWatchStateByIdUseCase.Params.createParams(it.id!!, false))
        }
    }

    fun onStateChanged() = view?.recreateLibrary()

    inner class WatchedTvShowObserver : DefaultSingleObserver<Pageable<WatchedTvShowModel>>() {
        override fun onSuccess(model: Pageable<WatchedTvShowModel>) {
            this@YourLibraryPresenter.view?.isLastPage = model.last
            if (model.content.isEmpty()) {
                this@YourLibraryPresenter.view?.apply {
                    loadImageBackground()
                    showEmptyLibraryMessage()
                }
            } else {
                this@YourLibraryPresenter.view?.apply {
                    setWatchedShows(model.content)
                    setPrimaryColorBackground()
                }
            }
            this@YourLibraryPresenter.view?.hideError()
        }

        override fun onError(error: Throwable) {
            this@YourLibraryPresenter.view?.showError()
        }
    }

    inner class UserStatsObserver : DefaultSingleObserver<UserStatsModel>() {
        override fun onSuccess(model: UserStatsModel) {
            this@YourLibraryPresenter.view?.setUserStats(model)
        }

        override fun onError(error: Throwable) {
            this@YourLibraryPresenter.view?.hideUserStatsView()
        }
    }

    inner class UpdateWatchStateObserver : DefaultCompletableObserver() {
        override fun onComplete() {
            // Get updated fullRuntime and execute UserRuntimeAfterChangeObserver
            this@YourLibraryPresenter.userRuntimeUseCase.execute(UserRuntimeAfterChangeObserver(), null)
        }
    }

    inner class UserRuntimeObserver : DefaultSingleObserver<Long>() {
        override fun onSuccess(model: Long) {
            this@YourLibraryPresenter.userRuntime = model
        }
    }

    inner class UserRuntimeAfterChangeObserver : DefaultSingleObserver<Long>() {
        override fun onSuccess(model: Long) {
            requestUserStats()
            this@YourLibraryPresenter.view?.showRuntimeNotification(oldUserRuntime = userRuntime, newRuntime = model)
            this@YourLibraryPresenter.userRuntime = model
        }

        override fun onError(error: Throwable) {
            requestUserStats()
            this@YourLibraryPresenter.view?.showRuntimeNotification(oldUserRuntime = userRuntime, newRuntime = 0)
            this@YourLibraryPresenter.userRuntime = 0
        }
    }
}
