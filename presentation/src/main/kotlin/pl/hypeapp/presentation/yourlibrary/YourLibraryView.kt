package pl.hypeapp.presentation.yourlibrary

import pl.hypeapp.domain.model.watched.UserStatsModel
import pl.hypeapp.domain.model.watched.WatchedTvShowModel
import pl.hypeapp.presentation.base.View

interface YourLibraryView : View {

    fun initNavigationDrawer()

    fun setWatchedShows(model: List<WatchedTvShowModel>)

    fun setUserStats(model: UserStatsModel)

    fun loadViewModel()

    fun initRecyclerAdapter()

    fun showError()

    fun showEmptyLibraryMessage()

    fun hideUserStatsView()

    fun loadImageBackground()

    fun setPrimaryColorBackground()

    fun hideError()

    var isLastPage: Boolean
}
