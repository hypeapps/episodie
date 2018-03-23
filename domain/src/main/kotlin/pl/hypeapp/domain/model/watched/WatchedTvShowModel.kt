package pl.hypeapp.domain.model.watched

import pl.hypeapp.domain.model.WatchState
import pl.hypeapp.domain.model.tvshow.TvShowModel

data class WatchedTvShowModel(val tvShowId: String,
                              var tvShowModel: TvShowModel? = null,
                              var runtime: Long?,
                              var watchState: String = WatchState.NOT_WATCHED,
                              var watchedEpisodesCount: Int = 0,
                              var watchedSeasonsCount: Int = 0,
                              var watchedSeasons: List<WatchedSeasonModel>?)

