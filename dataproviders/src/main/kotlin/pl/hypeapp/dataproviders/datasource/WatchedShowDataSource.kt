package pl.hypeapp.dataproviders.datasource

import android.annotation.SuppressLint
import pl.hypeapp.dataproviders.entity.room.*
import pl.hypeapp.dataproviders.service.room.RoomService
import pl.hypeapp.domain.model.Pageable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WatchedShowDataSource @Inject constructor(private val roomService: RoomService) : WatchedShowDataStore {

    override fun addTvShow(tvShow: WatchedTvShowEntity) = executeQuery {
        roomService.watchedShowDao.insertTvShow(tvShow)
        roomService.watchedShowDao.insertSeason(tvShow.seasons)
        roomService.watchedShowDao.insertEpisode(tvShow.seasons.flatMap { it.episodes })
    }

    override fun addSeason(vararg seasons: WatchedSeasonEntity) = executeQuery {
        roomService.watchedShowDao.insertSeason(seasons.asList())
        roomService.watchedShowDao.insertEpisode(seasons.flatMap { it.episodes })
    }

    override fun addEpisode(vararg episodes: WatchedEpisodeEntity) = executeQuery {
        roomService.watchedShowDao.insertEpisode(episodes.asList())
    }

    override fun deleteTvShow(tvShow: WatchedTvShowEntity) = executeQuery {
        roomService.watchedShowDao.deleteTvShow(tvShow)
    }

    override fun deleteEpisode(episode: WatchedEpisodeEntity) = executeQuery {
        roomService.watchedShowDao.deleteEpisode(episode)
    }

    override fun deleteSeason(season: WatchedSeasonEntity) = executeQuery {
        roomService.watchedShowDao.deleteSeason(season)
    }

    override fun getTvShowById(id: String): WatchedTvShowEntity? {
        return roomService.watchedShowDao.getTvShowById(id)
                .apply {
                    if (this != null)
                        seasons = roomService.watchedShowDao.getSeasonsByTvShowId(tvShowId)
                    this?.seasons?.forEach {
                        it.episodes = roomService.watchedShowDao.getEpisodesBySeasonId(it.seasonId)
                    }
                }
    }

    override fun getSeasonById(id: String): WatchedSeasonEntity {
        return roomService.watchedShowDao.getSeasonById(id)
                .apply {
                    episodes = roomService.watchedShowDao.getEpisodesBySeasonId(seasonId)
                }
    }

    override fun getEpisodeById(id: String): WatchedEpisodeEntity {
        return roomService.watchedShowDao.getEpisodeById(id)
    }

    override fun getWatchedEpisodesCountByTvShowId(tvShowId: String): WatchedEpisodesCountEntity {
        return roomService.watchedEpisodeDao.getWatchedEpisodesCountByTvShowId(tvShowId)
    }

    override fun getWatchedEpisodesCountByTvShowIds(tvShowIds: List<String>): List<WatchedEpisodesCountEntity> {
        return roomService.watchedEpisodeDao.getWatchedEpisodesCountByTvShowIds(tvShowIds)
    }

    override fun getWatchedEpisodesCountBySeasonIds(seasonsIds: List<String>): List<WatchedSeasonCountEntity> {
        return roomService.watchedEpisodeDao.getWatchedEpisodesCountBySeasonIds(seasonsIds)
    }

    override fun getWatchedEpisodesIdsBySeasonId(seasonId: String): List<String> {
        return roomService.watchedEpisodeDao.getWatchedEpisodesIdsBySeasonId(seasonId)
    }

    override fun getWatchedTvShowsSize(): Int {
        return roomService.watchedEpisodeDao.getWatchedTvShowsSize()
    }

    override fun getWatchedEpisodesByTvShowId(tvShowId: String): List<WatchedEpisodeEntity> {
        return roomService.watchedEpisodeDao.getWatchedEpisodesByTvShowId(tvShowId)
    }

    @SuppressLint("RestrictedApi")
    override fun getWatchedTvShows(page: Int, size: Int): Pageable<WatchedTvShowEntity> {
        val loadPageParams = PagingHelper.createLoadPageParams(
                page = page,
                size = size,
                totalItemCount = getWatchedTvShowsSize())
        val content = roomService.watchedShowDao
                .getWatchedTvShows()
                .loadRange(loadPageParams.startPosition, loadPageParams.size)
        return Pageable(content,
                totalPages = loadPageParams.totalPages,
                page = loadPageParams.page,
                totalElements = loadPageParams.totalItemCount,
                numberOfElements = loadPageParams.size,
                last = loadPageParams.last,
                first = loadPageParams.first)
    }

    private inline fun executeQuery(query: () -> Unit) {
        roomService.beginTransaction()
        try {
            query()
            roomService.setTransactionSuccessful()
        } finally {
            roomService.endTransaction()
        }
    }

}
