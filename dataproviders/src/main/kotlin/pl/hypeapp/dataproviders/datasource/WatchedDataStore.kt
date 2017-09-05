package pl.hypeapp.dataproviders.datasource

import pl.hypeapp.dataproviders.entity.room.WatchedEpisodeEntity
import pl.hypeapp.dataproviders.entity.room.WatchedEpisodesCountEntity
import pl.hypeapp.dataproviders.entity.room.WatchedSeasonCountEntity

interface WatchedDataStore {

    fun addTvShowToWatched(watchedEpisodeEntities: List<WatchedEpisodeEntity>)

    fun addSeasonToWatched(watchedEpisodeEntities: List<WatchedEpisodeEntity>)

    fun addEpisodeToWatched(watchedEpisodeEntity: WatchedEpisodeEntity)

    fun deleteTvShowFromWatched(tvShowId: String)

    fun deleteEpisodeFromWatched(episodeId: String)

    fun deleteSeasonFromWatched(seasonId: String)

    fun getWatchedEpisodesCountById(tvShowId: String): WatchedEpisodesCountEntity

    fun getWatchedEpisodesCountByTvShowIds(tvShowIds: List<String>): List<WatchedEpisodesCountEntity>

    fun getWatchedEpisodesCountBySeasonIds(seasonsIds: List<String>): List<WatchedSeasonCountEntity>

    fun getWatchEpisodesIdsBySeasonId(seasonId: String): List<String>

}
