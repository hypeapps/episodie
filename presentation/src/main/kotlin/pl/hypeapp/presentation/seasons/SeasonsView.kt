package pl.hypeapp.presentation.seasons

import pl.hypeapp.domain.model.AllSeasonsModel
import pl.hypeapp.presentation.base.View

interface SeasonsView : View {

    fun loadViewModel()

    fun initRecyclerAdapter()

    fun initSwipeToRefresh()

    fun populateRecyclerView(seasonsModel: AllSeasonsModel?)

    fun showError()

    fun showLoading()

    fun showEmptySeasonsMessage()

}
