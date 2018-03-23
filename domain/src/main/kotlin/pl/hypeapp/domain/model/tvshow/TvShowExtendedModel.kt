package pl.hypeapp.domain.model.tvshow

import pl.hypeapp.domain.extensions.sumByLong
import pl.hypeapp.domain.model.WatchState

data class TvShowExtendedModel(val tvShowId: String?,
                               val name: String?,
                               val premiered: String?,
                               val status: String?,
                               val fullRuntime: Long?,
                               val imageMedium: String?,
                               val imageOriginal: String?,
                               val seasons: List<SeasonModel>?) {

    var watchState: String = WatchState.NOT_WATCHED
        set(value) {
            when (value) {
                WatchState.WATCHED -> seasons?.forEach { it.watchState = WatchState.WATCHED }
                WatchState.NOT_WATCHED -> seasons?.forEach { it.watchState = WatchState.NOT_WATCHED }
            }
            field = value
        }

    var watchingRuntime: Long = 0
        get() = seasons?.sumByLong { it.watchingRuntime } ?: field

    var watchedEpisodes: Int = 0
        get() = seasons?.sumBy { it.watchedEpisodes } ?: field

    var watchedSeasons: Int = 0
        get() = seasons?.filter { it.watchState == WatchState.WATCHED }?.count() ?: field

    fun setSeasonWatchStateById(seasonId: String, watchState: String) {
        seasons?.find { it.seasonId == seasonId }?.watchState = watchState
        applyProperWatchState()
    }

    fun setEpisodeWatchStateById(episodeId: String, seasonId: String, watchState: String) {
        seasons?.find { it.seasonId == seasonId }?.setEpisodeWatchStateById(episodeId, watchState)
        applyProperWatchState()
    }

    private fun applyProperWatchState() = seasons?.let {
        when {
            it.all { it.watchState == WatchState.WATCHED } -> this.watchState = WatchState.WATCHED
            it.all { it.watchState == WatchState.NOT_WATCHED } -> this.watchState = WatchState.NOT_WATCHED
            else -> this.watchState = WatchState.PARTIALLY_WATCHED
        }
    }

}
