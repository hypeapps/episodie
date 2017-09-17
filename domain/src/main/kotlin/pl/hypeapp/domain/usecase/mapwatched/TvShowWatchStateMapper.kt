package pl.hypeapp.domain.usecase.mapwatched

import pl.hypeapp.domain.model.*
import pl.hypeapp.domain.repository.WatchedRepository
import javax.inject.Inject

class TvShowWatchStateMapper @Inject constructor(private val repository: WatchedRepository) {

    fun map(seasonsModel: AllSeasonsModel) = seasonsModel.seasons?.let {
        // get seasons ids for repository call
        val seasonsIds: List<String> = it.map { it.seasonId!! }
        // map all episodes watch state to not watched
        it.forEach {
            it.watchState = WatchState.NOT_WATCHED
            it.episodes?.map { it.watchState = WatchState.NOT_WATCHED }
        }
        val watchedSeasonsCount: List<WatchedEpisodesCountModel> = repository.getWatchedSeasonsCountByIds(seasonsIds)
        // apply proper season and episode watch state for all seasons which are present in repository
        watchedSeasonsCount.forEach { (id, count) ->
            it.filter { it.seasonId == id }
                    .map {
                        applyProperSeasonWatchState(count, it)
                        applyProperEpisodeWatchState(it)
                    }
        }
    }

    fun map(tvShows: List<TvShowModel>) = tvShows.let {
        val tvShowsIds: List<String> = it.map { it.id!! }
        val watchedEpisodesCount: List<WatchedEpisodesCountModel> = repository.getWatchedEpisodesCountByTvShowIds(tvShowsIds)
        tvShows.forEach { it.watchState = WatchState.NOT_WATCHED }
        watchedEpisodesCount.forEach { (tvShowId, count) ->
            it.filter { it.id == tvShowId }
                    .map { applyProperTvShowWatchState(count, it) }
        }
    }

    private fun applyProperTvShowWatchState(count: Int, tvShowModel: TvShowModel) = when (count) {
        tvShowModel.episodeOrder -> tvShowModel.watchState = WatchState.WATCHED
        else -> tvShowModel.watchState = WatchState.PARTIALLY_WATCHED
    }

    // If episode is present in repository apply WATCHED state
    private fun applyProperEpisodeWatchState(seasonModel: SeasonModel) {
        repository.getWatchedEpisodesIdsBySeasonId(seasonModel.seasonId!!).forEach { id ->
            seasonModel.episodes?.filter {
                it.episodeId == id
            }?.map {
                it.watchState = WatchState.WATCHED
            }
        }
    }

    // If watched episodes count match episode order assign state WATCHED else PARTIALLY state
    private fun applyProperSeasonWatchState(count: Int, seasonModel: SeasonModel) = when (count) {
        seasonModel.episodeOrder -> seasonModel.watchState = WatchState.WATCHED
        else -> seasonModel.watchState = WatchState.PARTIALLY_WATCHED
    }

}
