package pl.hypeapp.dataproviders.entity.mapper.watched

import pl.hypeapp.dataproviders.entity.mapper.Mapper
import pl.hypeapp.dataproviders.entity.room.WatchedEpisodeEntity
import pl.hypeapp.dataproviders.entity.room.WatchedSeasonEntity
import pl.hypeapp.dataproviders.entity.room.WatchedTvShowEntity
import pl.hypeapp.domain.model.Pageable
import pl.hypeapp.domain.model.tvshow.EpisodeModel
import pl.hypeapp.domain.model.tvshow.SeasonModel
import pl.hypeapp.domain.model.tvshow.TvShowExtendedModel
import pl.hypeapp.domain.model.watched.WatchedEpisodeModel
import pl.hypeapp.domain.model.watched.WatchedSeasonModel
import pl.hypeapp.domain.model.watched.WatchedTvShowModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WatchedTvShowModelMapper @Inject constructor(private val watchedSeasonModelMapper: WatchedSeasonModelMapper)
    : Mapper<WatchedTvShowEntity, TvShowExtendedModel>() {
    override fun transform(item: TvShowExtendedModel?): WatchedTvShowEntity {
        return WatchedTvShowEntity(
                tvShowId = item?.tvShowId!!,
                title = item.name ?: "",
                runtime = item.watchingRuntime,
                watchState = item.watchState,
                watchedEpisodesCount = item.watchedEpisodes,
                watchedSeasonsCount = item.watchedSeasons,
                seasons = watchedSeasonModelMapper.transform(item.seasons ?: emptyList())
        )
    }
}

@Singleton
class WatchedSeasonModelMapper @Inject constructor(private val watchedEpisodeModelMapper: WatchedEpisodeModelMapper)
    : Mapper<WatchedSeasonEntity, SeasonModel>() {
    override fun transform(item: SeasonModel?): WatchedSeasonEntity {
        return WatchedSeasonEntity(
                seasonId = item?.seasonId!!,
                tvShowId = item.tvShowId!!,
                watchState = item.watchState,
                runtime = item.watchingRuntime,
                watchedEpisodesCount = item.watchedEpisodes,
                episodes = watchedEpisodeModelMapper.transform(item.episodes ?: emptyList())
        )
    }
}

@Singleton
class WatchedEpisodeModelMapper @Inject constructor() : Mapper<WatchedEpisodeEntity, EpisodeModel>() {
    override fun transform(item: EpisodeModel?): WatchedEpisodeEntity {
        return WatchedEpisodeEntity(
                episodeId = item?.episodeId!!,
                seasonId = item.seasonId!!,
                watchState = item.watchState,
                tvShowId = item.tvShowId!!
        )
    }
}

@Singleton
class WatchedTvShowEntityMapper @Inject constructor(private val watchedSeasonEntityMapper: WatchedSeasonEntityMapper)
    : Mapper<WatchedTvShowModel, WatchedTvShowEntity>() {
    override fun transform(item: WatchedTvShowEntity?): WatchedTvShowModel? {
        return item?.let {
            WatchedTvShowModel(
                    tvShowId = it.tvShowId,
                    runtime = it.runtime,
                    watchState = it.watchState,
                    watchedSeasonsCount = it.watchedSeasonsCount,
                    watchedEpisodesCount = it.watchedEpisodesCount,
                    watchedSeasons = watchedSeasonEntityMapper.transform(item.seasons)
            )
        }
    }

    fun transform(item: Pageable<WatchedTvShowEntity>): Pageable<WatchedTvShowModel> {
        return Pageable(transform(item.content),
                item.last,
                item.totalPages,
                item.totalElements,
                item.size,
                item.page,
                item.first,
                item.numberOfElements)
    }
}

@Singleton
class WatchedSeasonEntityMapper @Inject constructor(private val watchedEpisodeEntityMapper: WatchedEpisodeEntityMapper)
    : Mapper<WatchedSeasonModel, WatchedSeasonEntity>() {
    override fun transform(item: WatchedSeasonEntity?): WatchedSeasonModel {
        return WatchedSeasonModel(
                tvShowId = item?.tvShowId!!,
                seasonId = item.seasonId,
                runtime = item.runtime,
                watchState = item.watchState,
                watchedEpisodes = watchedEpisodeEntityMapper.transform(item.episodes)
        )
    }
}

@Singleton
class WatchedEpisodeEntityMapper @Inject constructor() : Mapper<WatchedEpisodeModel, WatchedEpisodeEntity>() {
    override fun transform(item: WatchedEpisodeEntity?): WatchedEpisodeModel {
        return WatchedEpisodeModel(
                tvShowId = item?.tvShowId!!,
                seasonId = item.seasonId,
                episodeId = item.episodeId,
                watchState = item.watchState
        )
    }
}
