package pl.hypeapp.episodie.ui.viewmodel.bingewatching

import pl.hypeapp.episodie.ui.base.adapter.ViewType

data class RemainingTimeViewModel(val runtime: Long?,
                                  val isWatched: Boolean) : ViewType {

    override fun getViewType(): Int = ViewType.BingeWatchingViewType.TIME_REMAINING_ITEM

}
