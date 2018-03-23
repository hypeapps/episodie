package pl.hypeapp.episodie.ui.viewmodel.seasontracker

import pl.hypeapp.domain.model.WatchState
import pl.hypeapp.domain.model.tvshow.EpisodeModel
import pl.hypeapp.episodie.ui.base.adapter.ViewType

data class EpisodeViewModel(var episodeModel: EpisodeModel,
                            var isNextEpisodeWatched: String = WatchState.NOT_WATCHED,
                            var isLastEpisode: Boolean = false) : ViewType {

    override fun getViewType(): Int = ViewType.SeasonTrackerViewType.EPISODE_ITEM

}
