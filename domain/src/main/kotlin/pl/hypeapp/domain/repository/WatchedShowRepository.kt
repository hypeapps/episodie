package pl.hypeapp.domain.repository

import io.reactivex.Completable
import io.reactivex.Single
import pl.hypeapp.domain.model.Pageable
import pl.hypeapp.domain.model.tvshow.EpisodeModel
import pl.hypeapp.domain.model.tvshow.SeasonModel
import pl.hypeapp.domain.model.tvshow.TvShowExtendedModel
import pl.hypeapp.domain.model.watched.WatchedEpisodeModel
import pl.hypeapp.domain.model.watched.WatchedEpisodesCountModel
import pl.hypeapp.domain.model.watched.WatchedTvShowModel

interface WatchedShowRepository {

    fun addTvShowToWatched(tvShowModel: TvShowExtendedModel): Completable

    fun addSeasonToWatched(seasonModel: SeasonModel): Completable

    fun addEpisodeToWatched(episodeModel: EpisodeModel): Completable

    fun deleteWatchedTvShow(tvShowModel: TvShowExtendedModel): Completable

    fun deleteWatchedEpisode(episodeModel: EpisodeModel): Completable

    fun deleteWatchedSeason(seasonModel: SeasonModel): Completable

    fun getWatchedEpisodesCountById(tvShowId: String): WatchedEpisodesCountModel

    fun getWatchedEpisodesIdsBySeasonId(seasonId: String): List<String>

    fun getWatchedEpisodesCountByTvShowIds(tvShowsIds: List<String>): List<WatchedEpisodesCountModel>

    fun getWatchedSeasonsCountByIds(seasonsIds: List<String>): List<WatchedEpisodesCountModel>

    fun getWatchedTvShows(page: Int, size: Int): Single<Pageable<WatchedTvShowModel>>

    fun getEpisodesByTvShowId(tvShowId: String): List<WatchedEpisodeModel>

    fun getWatchedTvShowById(id: String): WatchedTvShowModel?
}
