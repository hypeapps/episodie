package pl.hypeapp.presentation.yourlibrary

import pl.hypeapp.domain.model.Pageable
import pl.hypeapp.domain.model.watched.UserStatsModel
import pl.hypeapp.domain.model.watched.WatchedTvShowModel
import pl.hypeapp.domain.usecase.base.DefaultSingleObserver
import pl.hypeapp.domain.usecase.userstats.UserStatsUseCase
import pl.hypeapp.domain.usecase.yourlibrary.GetWatchedTvShowUseCase
import pl.hypeapp.presentation.base.Presenter
import javax.inject.Inject

class YourLibraryPresenter @Inject constructor(private val getWatchedTvShowUseCase: GetWatchedTvShowUseCase,
                                               private val getUserStatsUseCase: UserStatsUseCase)
    : Presenter<YourLibraryView>() {

    companion object {
        const val PAGE_SIZE = 5
    }

    override fun onAttachView(view: YourLibraryView) {
        super.onAttachView(view)
        this.view?.initNavigationDrawer()
        this.view?.initRecyclerAdapter()
        this.view?.loadViewModel()
        requestUserStats()
    }

    override fun onDetachView() {
        super.onDetachView()
        getWatchedTvShowUseCase.dispose()
        getUserStatsUseCase.dispose()
    }

    fun requestModel(page: Int, update: Boolean) {
        view?.let {
            if (!it.isLastPage) {
                getWatchedTvShowUseCase.execute(WatchedTvShowObserver(), GetWatchedTvShowUseCase.Params
                        .createQuery(page, PAGE_SIZE, update))
            }
        }
    }

    fun requestUserStats() = getUserStatsUseCase.execute(UserStatsObserver(), null)

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
}
