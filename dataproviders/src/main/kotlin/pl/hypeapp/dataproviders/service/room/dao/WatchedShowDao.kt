package pl.hypeapp.dataproviders.service.room.dao

import android.arch.paging.TiledDataSource
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import pl.hypeapp.dataproviders.entity.room.WatchedEpisodeEntity
import pl.hypeapp.dataproviders.entity.room.WatchedSeasonEntity
import pl.hypeapp.dataproviders.entity.room.WatchedTvShowEntity

@Dao
interface WatchedShowDao {

    @Insert(onConflict = REPLACE)
    fun insertTvShow(vararg tvShow: WatchedTvShowEntity)

    @Insert(onConflict = REPLACE)
    fun insertSeason(season: List<WatchedSeasonEntity>)

    @Insert(onConflict = REPLACE)
    fun insertEpisode(episodes: List<WatchedEpisodeEntity>)

    @Delete
    fun deleteTvShow(vararg tvShows: WatchedTvShowEntity)

    @Delete
    fun deleteSeason(vararg seasons: WatchedSeasonEntity)

    @Delete
    fun deleteEpisode(vararg episode: WatchedEpisodeEntity)

    @Query("SELECT * FROM watched_tv_shows WHERE tv_show_id = :arg0")
    fun getTvShowById(tvShowId: String): WatchedTvShowEntity?

    @Query("SELECT * FROM watched_seasons WHERE season_id = :arg0")
    fun getSeasonById(seasonId: String): WatchedSeasonEntity

    @Query("SELECT * FROM watched_episodes WHERE episode_id = :arg0")
    fun getEpisodeById(episodeId: String): WatchedEpisodeEntity

    @Query("SELECT * FROM watched_seasons WHERE tv_show_id = :arg0")
    fun getSeasonsByTvShowId(tvShowId: String): List<WatchedSeasonEntity>

    @Query("SELECT * FROM watched_episodes WHERE tv_show_id = :arg0")
    fun getEpisodesByTvShowId(tvShowId: String): List<WatchedEpisodeEntity>

    @Query("SELECT * FROM watched_episodes WHERE season_id = :arg0")
    fun getEpisodesBySeasonId(seasonId: String): List<WatchedEpisodeEntity>

    @Query("SELECT * from watched_tv_shows")
    fun getWatchedTvShows(): TiledDataSource<WatchedTvShowEntity>

    @Query("SELECT COUNT(*) from watched_tv_shows")
    fun getWatchedTvShowsSize(): Int
}
