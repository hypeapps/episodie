package pl.hypeapp.dataproviders.datasource

import pl.hypeapp.dataproviders.entity.room.WatchedEpisodeEntity
import pl.hypeapp.dataproviders.entity.room.WatchedSeasonEntity
import pl.hypeapp.dataproviders.entity.room.WatchedTvShowEntity
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

    fun getWatchedTvShows(page: Int, size: Int): Pageable<WatchedTvShowEntity>

    fun getWatchedTvShowsSize(): Int

}
