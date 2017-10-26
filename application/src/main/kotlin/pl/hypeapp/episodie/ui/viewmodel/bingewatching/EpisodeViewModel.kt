package pl.hypeapp.episodie.ui.viewmodel.bingewatching

import pl.hypeapp.domain.model.EpisodeModel
import pl.hypeapp.episodie.ui.base.adapter.ViewType

data class EpisodeViewModel(var episodeModel: EpisodeModel,
                            var isWatched: Boolean = false) : ViewType {

    override fun getViewType(): Int = ViewType.BingeWatchingViewType.EPISODE_ITEM

}
