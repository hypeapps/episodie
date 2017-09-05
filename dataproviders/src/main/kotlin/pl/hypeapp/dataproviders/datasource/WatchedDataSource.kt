package pl.hypeapp.dataproviders.datasource

import pl.hypeapp.dataproviders.entity.room.WatchedEpisodeEntity
import pl.hypeapp.dataproviders.entity.room.WatchedEpisodesCountEntity
import pl.hypeapp.dataproviders.entity.room.WatchedSeasonCountEntity
import pl.hypeapp.dataproviders.service.room.RoomService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WatchedDataSource @Inject constructor(private val roomService: RoomService) : WatchedDataStore {

    override fun addTvShowToWatched(watchedEpisodeEntities: List<WatchedEpisodeEntity>) = executeQuery {
        roomService.watchedEpisodeDao.insertWatchedEpisodes(watchedEpisodeEntities)
    }

    override fun addSeasonToWatched(watchedEpisodeEntities: List<WatchedEpisodeEntity>) = executeQuery {
        roomService.watchedEpisodeDao.insertWatchedEpisodes(watchedEpisodeEntities)
    }

    override fun addEpisodeToWatched(watchedEpisodeEntity: WatchedEpisodeEntity) = executeQuery {
        roomService.watchedEpisodeDao.insertWatchedEpisode(watchedEpisodeEntity)
    }

    override fun deleteTvShowFromWatched(tvShowId: String) = executeQuery {
        roomService.watchedEpisodeDao.deleteWatchedTvShow(tvShowId)
    }

    override fun deleteEpisodeFromWatched(episodeId: String) = executeQuery {
        roomService.watchedEpisodeDao.deleteWatchedEpisode(episodeId)
    }

    override fun deleteSeasonFromWatched(seasonId: String) = executeQuery {
        roomService.watchedEpisodeDao.deleteWatchedSeason(seasonId)
    }

    override fun getWatchedEpisodesCountById(tvShowId: String): WatchedEpisodesCountEntity {
        return roomService.watchedEpisodeDao.getWatchedEpisodesCountById(tvShowId)
    }

    override fun getWatchedEpisodesCountByTvShowIds(tvShowIds: List<String>): List<WatchedEpisodesCountEntity> {
        return roomService.watchedEpisodeDao.getWatchedEpisodesCountByTvShowIds(tvShowIds)
    }

    override fun getWatchedEpisodesCountBySeasonIds(seasonsIds: List<String>): List<WatchedSeasonCountEntity> {
        return roomService.watchedEpisodeDao.getWatchedEpisodesCountBySeasonIds(seasonsIds)
    }

    override fun getWatchEpisodesIdsBySeasonId(seasonId: String): List<String> {
        return roomService.watchedEpisodeDao.getWatchEpisodesIdsBySeasonId(seasonId)
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
