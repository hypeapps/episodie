package pl.hypeapp.dataproviders.entity.mapper

import pl.hypeapp.dataproviders.entity.room.WatchedEpisodeEntity
import pl.hypeapp.domain.model.EpisodeModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WatchedEpisodeModelMapper @Inject constructor() : EntityMapper<WatchedEpisodeEntity, EpisodeModel>() {

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun transform(model: EpisodeModel?): WatchedEpisodeEntity {
        model!!.let {
            return WatchedEpisodeEntity(
                    episodeId = it.episodeId!!,
                    seasonId = it.seasonId!!,
                    tvShowId = it.tvShowId!!,
                    runtime = it.runtime!!)
        }
    }

}
