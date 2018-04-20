package pl.hypeapp.domain.model.watched

import pl.hypeapp.domain.model.WatchState

data class WatchedEpisodeModel(val tvShowId: String,
                               val episodeId: String,
                               val seasonId: String,
                               var watchState: String = WatchState.NOT_WATCHED)

