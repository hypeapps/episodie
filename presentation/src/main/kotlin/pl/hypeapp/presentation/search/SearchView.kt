package pl.hypeapp.presentation.search

import pl.hypeapp.domain.model.search.BasicSearchResultModel
import pl.hypeapp.domain.model.tvshow.TvShowModel
import pl.hypeapp.presentation.base.View

interface SearchView : View {

    fun navigateToTvShowDetails(tvShowModel: TvShowModel)

    fun initSearchView()

    fun populateRecyclerWithSuggestions(suggestions: List<BasicSearchResultModel>)

    fun initNavigationDrawer()

    fun dismissSearchView()

    fun loadViewModel()

    fun initRecyclerAdapter()
    fun setSearchViewSuggestions(suggestions: Array<String>)
    fun setNavigationBarOptions()
    fun showErrorToast()
}
