package pl.hypeapp.dataproviders.repository

import io.reactivex.Completable
import pl.hypeapp.dataproviders.datasource.DataFactory
import pl.hypeapp.dataproviders.entity.mapper.WatchedEpisodeEntityMapper
import pl.hypeapp.dataproviders.entity.mapper.WatchedEpisodesCountModelMapper
import pl.hypeapp.dataproviders.entity.mapper.WatchedSeasonCountModelMapper
import pl.hypeapp.dataproviders.entity.room.WatchedEpisodesCountEntity
import pl.hypeapp.dataproviders.entity.room.WatchedSeasonCountEntity
import pl.hypeapp.domain.model.EpisodeModel
import pl.hypeapp.domain.model.WatchedEpisodesCountModel
import pl.hypeapp.domain.repository.WatchedRepository
import javax.inject.Inject

class WatchedDataRepository @Inject constructor(private val dataFactory: DataFactory,
                                                private val watchedEpisodeEntityMapper: WatchedEpisodeEntityMapper,
                                                private val watchedEpisodesCountModelMapper: WatchedEpisodesCountModelMapper,
                                                private val watchedSeasonCountModelMapper: WatchedSeasonCountModelMapper)
    : WatchedRepository {

    override fun addEpisodeToWatched(episodeModel: EpisodeModel): Completable {
        return Completable.fromAction {
            dataFactory
                    .createWatchedDataSource()
                    .addEpisodeToWatched(watchedEpisodeEntityMapper.transform(episodeModel))
        }
    }

    override fun addTvShowToWatched(episodeModels: ArrayList<EpisodeModel>): Completable {
        return Completable.fromAction {
            dataFactory
                    .createWatchedDataSource()
                    .addTvShowToWatched(watchedEpisodeEntityMapper.transform(episodeModels))
        }
    }

    override fun addSeasonToWatched(seasonsEpisodes: ArrayList<EpisodeModel>): Completable {
        return Completable.fromAction {
            dataFactory
                    .createWatchedDataSource()
                    .addSeasonToWatched(watchedEpisodeEntityMapper.transform(seasonsEpisodes))
        }
    }

    override fun deleteWatchedTvShow(tvShowId: String): Completable {
        return Completable.fromAction {
            dataFactory
                    .createWatchedDataSource()
                    .deleteTvShowFromWatched(tvShowId)
        }
    }

    override fun deleteWatchedSeason(seasonId: String): Completable {
        return Completable.fromAction {
            dataFactory
                    .createWatchedDataSource()
                    .deleteSeasonFromWatched(seasonId)
        }
    }

    override fun deleteWatchedEpisode(episodeId: String): Completable {
        return Completable.fromAction {
            dataFactory
                    .createWatchedDataSource()
                    .deleteEpisodeFromWatched(episodeId)
        }
    }

    override fun getWatchedEpisodesCountById(tvShowId: String): WatchedEpisodesCountModel {
        val watchedEpisodesCountEntity: WatchedEpisodesCountEntity = dataFactory
                .createWatchedDataSource()
                .getWatchedEpisodesCountByTvShowId(tvShowId)
        return watchedEpisodesCountModelMapper.transform(watchedEpisodesCountEntity)
    }

    override fun getWatchedEpisodesIdsBySeasonId(seasonId: String): List<String> {
        return dataFactory
                .createWatchedDataSource()
                .getWatchedEpisodesIdsBySeasonId(seasonId)
    }

    override fun getWatchedEpisodesCountByTvShowIds(tvShowsIds: List<String>): List<WatchedEpisodesCountModel> {
        val watchedEpisodesCountEntites: List<WatchedEpisodesCountEntity> = dataFactory
                .createWatchedDataSource()
                .getWatchedEpisodesCountByTvShowIds(tvShowsIds)
        return watchedEpisodesCountModelMapper.transform(watchedEpisodesCountEntites)
    }

    override fun getWatchedSeasonsCountByIds(seasonsIds: List<String>): List<WatchedEpisodesCountModel> {
        val watchedSeasonsCountEntites: List<WatchedSeasonCountEntity> = dataFactory
                .createWatchedDataSource()
                .getWatchedEpisodesCountBySeasonIds(seasonsIds)
        return watchedSeasonCountModelMapper.transform(watchedSeasonsCountEntites)
    }

}
