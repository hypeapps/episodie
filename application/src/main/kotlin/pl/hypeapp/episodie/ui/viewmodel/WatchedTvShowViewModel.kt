package pl.hypeapp.episodie.ui.viewmodel

import pl.hypeapp.domain.model.tvshow.TvShowModel

class WatchedTvShowViewModel(tvShow: TvShowModel?,
                             val watchedEpisodes: Int = 0,
                             val watchedSeasons: Int = 0,
                             val watchingTime: Long = 0) : TvShowViewModel(tvShow)
