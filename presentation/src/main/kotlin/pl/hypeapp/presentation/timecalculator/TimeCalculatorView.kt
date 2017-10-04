package pl.hypeapp.presentation.timecalculator

import pl.hypeapp.domain.model.BasicSearchResultModel
import pl.hypeapp.domain.model.TvShowModel
import pl.hypeapp.presentation.base.View

interface TimeCalculatorView : View {

    fun initSearchView()

    fun initAdapter()

    fun startEnterAnimation()

    fun setSearchSuggestions(suggestions: Array<String>)

    fun showSuggestionsDialog(suggestions: Array<String?>)

    fun setRecyclerItem(item: BasicSearchResultModel)

    fun setRecyclerItemWithDelay(item: BasicSearchResultModel, delay: Long)

    fun deleteRecyclerItemAt(adapterPosition: Int)

    fun dismissSearchView()

    fun showErrorToast()

    fun showTvShowAlreadyAddedToast()

    fun initNavigationDrawer()

    fun addRuntimeWithAnimation(episodeRuntime: Long)

    fun subtractRuntimeWithAnimation(episodeRuntime: Long)

    fun decrementSelected()

    fun incrementSelected()

    fun addEpisodeOrder(episodeOrder: Int)

    fun subtractEpisodeOrder(episodeOrder: Int)

    fun openTvShowDetailsActivity(model: TvShowModel)
}
