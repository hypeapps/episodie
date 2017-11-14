package pl.hypeapp.presentation.seasons

import pl.hypeapp.domain.model.TvShowExtendedModel
import pl.hypeapp.presentation.base.View

interface SeasonsView : View {

    fun loadViewModel()

    fun initRecyclerAdapter()

    fun initSwipeToRefresh()

    fun updateRecyclerList(seasonsModel: TvShowExtendedModel?)

    fun populateRecyclerView(seasonsModel: TvShowExtendedModel?)

    fun showError()

    fun showLoading()

    fun showEmptySeasonsMessage()

    fun onChangedWatchState()

    fun onChangeWatchStateError()

    fun observeWatchStateInParentActivity()

    fun startLoadingAnimation()
}
