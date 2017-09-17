package pl.hypeapp.presentation.toplist

import pl.hypeapp.domain.model.TopListModel
import pl.hypeapp.domain.model.TvShowModel
import pl.hypeapp.presentation.base.View

interface TopListView : View {

    fun initSwipeRefreshLayout()

    fun initRecyclerAdapter()

    fun loadBackdrop()

    fun observeDragDrawer()

    fun observeActivityReenter()

    fun loadViewModel()

    fun populateRecyclerList(topListModel: TopListModel?)

    fun updateRecyclerList(tvShows: List<TvShowModel>)

    fun animateDrawerHamburgerArrow(progress: Float)

    fun showError()

    fun onChangeWatchStateError()

    fun showRuntimeNotification(oldUserRuntime: Long, newRuntime: Long)

}
