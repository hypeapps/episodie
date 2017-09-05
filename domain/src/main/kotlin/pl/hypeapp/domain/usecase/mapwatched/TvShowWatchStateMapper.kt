package pl.hypeapp.domain.usecase.mapwatched

import pl.hypeapp.domain.model.*
import pl.hypeapp.domain.repository.WatchedRepository
import java.util.logging.Logger
import javax.inject.Inject

class TvShowWatchStateMapper @Inject constructor(private val repository: WatchedRepository) {

    val log: Logger = Logger.getAnonymousLogger()

    fun map(tvShowId: String) {
//        log.info(" COUNT " + repository.getWatchedEpisodesCount(id))
    }

    fun map(seasonsModel: AllSeasonsModel) = seasonsModel.seasons?.let {
        val seasonsIds: List<String> = it.map { it.seasonId!! }
        it.forEach {
            it.watchState = WatchState.NOT_WATCHED
            it.episodes?.map { it.watchState = WatchState.NOT_WATCHED }
        }
        val watchedSeasonsCount: List<WatchedEpisodesCountModel> = repository.getWatchedSeasonsCountByIds(seasonsIds)
        watchedSeasonsCount.forEach { (id, count) ->
            it.filter { it.seasonId == id }
                    .map {
                        applyProperSeasonWatchState(count, it)
                        applyProperEpisodeWatchState(it)
                    }
        }
        it.forEach {
            log.info(" SESAONS INFO " + it.seasonNumber + " watch state " + it.watchState)
        }
    }

    fun map(tvShows: List<TvShowModel>) = tvShows.let {
        val tvShowsIds: List<String> = it.map { it.id!! }
        log.info(" SIZE " + tvShowsIds.size)
        log.info(" SIZE REPO " + repository.getWatchedEpisodesCountByTvShowIds(tvShowsIds).size)
        val watchedEpisodesCount: List<WatchedEpisodesCountModel> = repository.getWatchedEpisodesCountByTvShowIds(tvShowsIds)
        tvShows.forEach { it.watchState = WatchState.NOT_WATCHED }
        watchedEpisodesCount.forEach { (tvShowId, count) ->
            it.filter { it.id == tvShowId }
                    .map {
                        log.info("tv model " + it.episodeOrder + " count " + count)
                        applyProperTvShowWatchState(count, it)
                    }
        }
        tvShows.forEach {
            log.info(" INFO " + it.name + " " + it.watchState)
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
