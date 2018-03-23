package pl.hypeapp.domain.model.watched

data class UserStatsModel(val watchingTime: Long,
                          val watchedTvShowsCount: Int,
                          val watchedEpisodesCount: Int,
                          val watchedSeasonsCount: Int)
