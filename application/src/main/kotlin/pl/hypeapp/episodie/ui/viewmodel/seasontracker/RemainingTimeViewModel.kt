package pl.hypeapp.episodie.ui.viewmodel.seasontracker

import pl.hypeapp.domain.model.WatchState
import pl.hypeapp.episodie.ui.base.adapter.ViewType

data class RemainingTimeViewModel(val runtime: Long?,
                                  val watchState: Int = WatchState.NOT_WATCHED) : ViewType {

    override fun getViewType(): Int = ViewType.SeasonTrackerViewType.TIME_REMAINING_ITEM

}
