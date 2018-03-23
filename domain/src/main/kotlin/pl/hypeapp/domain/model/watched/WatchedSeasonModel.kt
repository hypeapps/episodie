package pl.hypeapp.domain.model.watched

import pl.hypeapp.domain.model.WatchState

data class WatchedSeasonModel(var seasonId: String,
                              var tvShowId: String,
                              var runtime: Long?,
                              var watchState: String = WatchState.NOT_WATCHED,
                              var watchedEpisodes: List<WatchedEpisodeModel>? = null)
