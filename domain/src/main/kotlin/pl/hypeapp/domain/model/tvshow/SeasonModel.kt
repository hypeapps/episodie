package pl.hypeapp.domain.model.tvshow

import pl.hypeapp.domain.extensions.sumByLong
import pl.hypeapp.domain.model.WatchState

class SeasonModel constructor(val seasonId: String?,
                              val tvShowId: String?,
                              val seasonNumber: Int?,
                              val episodeOrder: Int?,
                              val fullRuntime: Long?,
                              val premiereDate: String?,
                              val imageMedium: String?,
                              val episodes: List<EpisodeModel>?) {

    var watchState: String = WatchState.NOT_WATCHED
        set(value) {
            when (value) {
                WatchState.WATCHED -> episodes?.forEach { it.watchState = WatchState.WATCHED }
                WatchState.NOT_WATCHED -> episodes?.forEach { it.watchState = WatchState.NOT_WATCHED }
            }
            field = value
        }

    var watchingRuntime: Long = 0
        get() = episodes
                ?.filter { it.watchState == WatchState.WATCHED }
                ?.sumByLong { it.runtime ?: 0 } ?: field

    var watchedEpisodes: Int = 0
        get() = episodes?.filter { it.watchState == WatchState.WATCHED }?.count() ?: field

    fun setEpisodeWatchStateById(episodeId: String, watchState: String) {
        episodes?.find { it.episodeId == episodeId }?.watchState = watchState
        applyProperWatchState()
    }

    private fun applyProperWatchState() = episodes?.let {
        when {
            it.all { it.watchState == WatchState.WATCHED } -> this.watchState = WatchState.WATCHED
            it.all { it.watchState == WatchState.NOT_WATCHED } -> this.watchState = WatchState.NOT_WATCHED
            else -> this.watchState = WatchState.PARTIALLY_WATCHED
        }
    }
}
