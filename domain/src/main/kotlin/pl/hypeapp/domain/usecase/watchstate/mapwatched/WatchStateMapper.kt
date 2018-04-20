package pl.hypeapp.domain.usecase.watchstate.mapwatched

import pl.hypeapp.domain.model.WatchState
import pl.hypeapp.domain.model.tvshow.TvShowExtendedModel
import pl.hypeapp.domain.model.tvshow.TvShowModel
import pl.hypeapp.domain.repository.WatchedShowRepository
import javax.inject.Inject

class WatchStateMapper @Inject constructor(private val watchedShowRepository: WatchedShowRepository) {

    fun map(tvShow: TvShowExtendedModel) {
        // Get watched tv show from repository
        tvShow.watchState = WatchState.NOT_WATCHED
        with(watchedShowRepository.getWatchedTvShowById(tvShow.tvShowId
                ?: throw Throwable("id cannot be null")) ?: return) {
            // Apply proper tv show watch state
            tvShow.watchState = watchState
            watchedSeasons?.forEach {
                tvShow.seasons
                        ?.find { seasonModel -> seasonModel.seasonId == it.seasonId }
                        ?.let { seasonModel ->
                            seasonModel.watchState = it.watchState
                            seasonModel.episodes
                        }
                        ?.map { episodeModel ->
                            episodeModel.watchState = it.watchedEpisodes?.find { watchedEpisodeModel ->
                                watchedEpisodeModel.episodeId == episodeModel.episodeId
                            }?.watchState ?: WatchState.NOT_WATCHED
                        }
            }
        }
    }

    fun map(tvShowModel: TvShowModel) {
        tvShowModel.watchState = WatchState.NOT_WATCHED
        with(watchedShowRepository.getWatchedTvShowById(tvShowModel.id
                ?: throw Throwable("id cannot be null")) ?: return) {
            tvShowModel.watchState = watchState
            runtime?.let { tvShowModel.watchingTime = it }
            tvShowModel.watchedEpisodes = watchedEpisodesCount
        }
    }

}
