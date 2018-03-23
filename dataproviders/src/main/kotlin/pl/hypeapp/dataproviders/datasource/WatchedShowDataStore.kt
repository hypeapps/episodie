package pl.hypeapp.dataproviders.datasource

import pl.hypeapp.dataproviders.entity.room.*
import pl.hypeapp.domain.model.Pageable

interface WatchedShowDataStore {

    fun addTvShow(tvShow: WatchedTvShowEntity)

    fun addSeason(vararg seasons: WatchedSeasonEntity)

    fun addEpisode(vararg episodes: WatchedEpisodeEntity)

    fun deleteTvShow(tvShow: WatchedTvShowEntity)

    fun deleteEpisode(episode: WatchedEpisodeEntity)

    fun deleteSeason(season: WatchedSeasonEntity)

    fun getTvShowById(id: String): WatchedTvShowEntity?

    fun getSeasonById(id: String): WatchedSeasonEntity

    fun getEpisodeById(id: String): WatchedEpisodeEntity

    fun getWatchedEpisodesCountByTvShowId(tvShowId: String): WatchedEpisodesCountEntity

    fun getWatchedEpisodesCountByTvShowIds(tvShowIds: List<String>): List<WatchedEpisodesCountEntity>

    fun getWatchedEpisodesCountBySeasonIds(seasonsIds: List<String>): List<WatchedSeasonCountEntity>

    fun getWatchedEpisodesIdsBySeasonId(seasonId: String): List<String>

    fun getWatchedTvShows(page: Int, size: Int): Pageable<WatchedTvShowEntity>

    fun getWatchedEpisodesByTvShowId(tvShowId: String): List<WatchedEpisodeEntity>

    fun getWatchedTvShowsSize(): Int

}
