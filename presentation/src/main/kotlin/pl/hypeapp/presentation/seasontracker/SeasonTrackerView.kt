package pl.hypeapp.presentation.seasontracker

import pl.hypeapp.domain.model.collections.SeasonTrackerModel
import pl.hypeapp.domain.model.tvshow.SeasonModel
import pl.hypeapp.domain.model.tvshow.TvShowExtendedModel
import pl.hypeapp.presentation.base.View

interface SeasonTrackerView : View {

    fun getModel(): SeasonTrackerModel?

    fun retainViewModel(seasonTrackerModel: SeasonTrackerModel)

    fun retainSeasonViewModel(season: SeasonModel, tvShowExtendedModel: TvShowExtendedModel)

    fun clearModel()

    fun initSeasonTracker()

    fun initSearchView()

    fun dismissSearchView()

    fun showSearchComponent()

    fun showErrorToast()

    fun setSearchSuggestions(suggestions: Array<String>)

    fun showSuggestionsDialog(suggestions: Array<String?>)

    fun showSelectSeasonDialog(tvShowExtendedModel: TvShowExtendedModel)

    fun loadTvShowHeader()

    fun showLoading()

    fun hideLoading()

    fun hideSearch()

    fun initNavigationDrawer()

    fun scheduleNotifications()

    fun cancelNotifications()

    fun updateRecyclerView()

    fun showErrorView()

    fun hideErrorView()

    fun onRetry()
}
