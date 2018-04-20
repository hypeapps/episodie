package pl.hypeapp.dataproviders.repository

import io.reactivex.Completable
import io.reactivex.Single
import pl.hypeapp.dataproviders.datasource.DataFactory
import pl.hypeapp.dataproviders.entity.mapper.watched.WatchedEpisodeModelMapper
import pl.hypeapp.dataproviders.entity.mapper.watched.WatchedSeasonModelMapper
import pl.hypeapp.dataproviders.entity.mapper.watched.WatchedTvShowEntityMapper
import pl.hypeapp.dataproviders.entity.mapper.watched.WatchedTvShowModelMapper
import pl.hypeapp.domain.model.Pageable
import pl.hypeapp.domain.model.tvshow.EpisodeModel
import pl.hypeapp.domain.model.tvshow.SeasonModel
import pl.hypeapp.domain.model.tvshow.TvShowExtendedModel
import pl.hypeapp.domain.model.watched.WatchedTvShowModel
import pl.hypeapp.domain.repository.WatchedShowRepository
import javax.inject.Inject

class WatchedShowDataRepository @Inject constructor(private val dataFactory: DataFactory,
                                                    private val watchedTvShowModelMapper: WatchedTvShowModelMapper,
                                                    private val watchedSeasonModelMapper: WatchedSeasonModelMapper,
                                                    private val watchedEpisodeModelMapper: WatchedEpisodeModelMapper,
                                                    private val watchedTvShowEntityMapper: WatchedTvShowEntityMapper)
    : WatchedShowRepository {

    override fun addTvShowToWatched(tvShowModel: TvShowExtendedModel): Completable = Completable.fromAction {
        dataFactory.createWatchedShowDataSource()
                .addTvShow(watchedTvShowModelMapper.transform(tvShowModel))
    }

    override fun addSeasonToWatched(seasonModel: SeasonModel): Completable = Completable.fromAction {
        dataFactory.createWatchedShowDataSource()
                .addSeason(watchedSeasonModelMapper.transform(seasonModel))
    }

    override fun addEpisodeToWatched(episodeModel: EpisodeModel): Completable = Completable.fromAction {
        dataFactory.createWatchedShowDataSource()
                .addEpisode(watchedEpisodeModelMapper.transform(episodeModel))
    }

    override fun deleteWatchedTvShow(tvShowModel: TvShowExtendedModel): Completable = Completable.fromAction {
        dataFactory.createWatchedShowDataSource()
                .deleteTvShow(watchedTvShowModelMapper.transform(tvShowModel))
    }

    override fun deleteWatchedSeason(seasonModel: SeasonModel): Completable = Completable.fromAction {
        dataFactory.createWatchedShowDataSource()
                .deleteSeason(watchedSeasonModelMapper.transform(seasonModel))
    }

    override fun deleteWatchedEpisode(episodeModel: EpisodeModel): Completable = Completable.fromAction {
        dataFactory.createWatchedShowDataSource()
                .deleteEpisode(watchedEpisodeModelMapper.transform(episodeModel))
    }

    override fun getWatchedTvShowById(id: String): WatchedTvShowModel? {
        return watchedTvShowEntityMapper.transform(dataFactory.createWatchedShowDataSource()
                .getTvShowById(id))
    }

    override fun getWatchedTvShows(page: Int, size: Int): Single<Pageable<WatchedTvShowModel>> {
        return Single.fromCallable { dataFactory.createWatchedShowDataSource().getWatchedTvShows(page, size) }
                .map { watchedTvShowEntityMapper.transform(it) }
    }

}
