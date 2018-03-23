package pl.hypeapp.domain.repository

import io.reactivex.Completable
import io.reactivex.Single
import pl.hypeapp.domain.model.Pageable
import pl.hypeapp.domain.model.tvshow.EpisodeModel
import pl.hypeapp.domain.model.tvshow.SeasonModel
import pl.hypeapp.domain.model.tvshow.TvShowExtendedModel
import pl.hypeapp.domain.model.watched.WatchedTvShowModel

interface WatchedShowRepository {

    fun addTvShowToWatched(tvShowModel: TvShowExtendedModel): Completable

    fun addSeasonToWatched(seasonModel: SeasonModel): Completable

    fun addEpisodeToWatched(episodeModel: EpisodeModel): Completable

    fun deleteWatchedTvShow(tvShowModel: TvShowExtendedModel): Completable

    fun deleteWatchedEpisode(episodeModel: EpisodeModel): Completable

    fun deleteWatchedSeason(seasonModel: SeasonModel): Completable

    fun getWatchedTvShows(page: Int, size: Int): Single<Pageable<WatchedTvShowModel>>

    fun getWatchedTvShowById(id: String): WatchedTvShowModel?
}
