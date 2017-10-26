package pl.hypeapp.episodie.ui.viewmodel.bingewatching

import pl.hypeapp.domain.model.SeasonModel
import pl.hypeapp.episodie.ui.base.adapter.ViewType

data class HeaderViewModel(val seasonModel: SeasonModel,
                           var watchedEpisodes: Int,
                           var watchedRuntime: Long) : ViewType {

    override fun getViewType(): Int = ViewType.BingeWatchingViewType.HEADER

}
