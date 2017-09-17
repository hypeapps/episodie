package pl.hypeapp.domain.repository

import io.reactivex.Completable
import pl.hypeapp.domain.model.EpisodeModel
import pl.hypeapp.domain.model.WatchedEpisodesCountModel

interface WatchedRepository {

    fun addTvShowToWatched(episodeModels: ArrayList<EpisodeModel>): Completable

    fun addEpisodeToWatched(episodeModel: EpisodeModel): Completable

    fun addSeasonToWatched(seasonsEpisodes: ArrayList<EpisodeModel>): Completable

    fun deleteWatchedTvShow(tvShowId: String): Completable

    fun deleteWatchedEpisode(episodeId: String): Completable

    fun deleteWatchedSeason(seasonId: String): Completable

    fun getWatchedEpisodesCountById(tvShowId: String): WatchedEpisodesCountModel

    fun getWatchedEpisodesIdsBySeasonId(seasonId: String): List<String>

    fun getWatchedEpisodesCountByTvShowIds(tvShowsIds: List<String>): List<WatchedEpisodesCountModel>

    fun getWatchedSeasonsCountByIds(seasonsIds: List<String>): List<WatchedEpisodesCountModel>

}
