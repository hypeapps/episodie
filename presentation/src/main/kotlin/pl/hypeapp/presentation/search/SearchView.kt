package pl.hypeapp.presentation.search

import pl.hypeapp.domain.model.tvshow.TvShowModel
import pl.hypeapp.presentation.base.View

interface SearchView : View {

    fun navigateToTvShowDetails(tvShowModel: TvShowModel)

    fun initSearchView()

    fun populateRecyclerWithSuggestions(suggestions: List<TvShowModel>)

    fun initNavigationDrawer()

    fun dismissSearchView()

    fun loadViewModel()

    fun initRecyclerAdapter()

    fun showRuntimeNotification(oldUserRuntime: Long, newRuntime: Long)

    fun setSearchViewSuggestions(suggestions: Array<String>)

    fun setNavigationBarOptions()

    fun showErrorToast()

    fun onChangeWatchStateError()
}
