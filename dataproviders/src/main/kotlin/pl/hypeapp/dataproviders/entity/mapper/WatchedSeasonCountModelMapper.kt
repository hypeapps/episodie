package pl.hypeapp.dataproviders.entity.mapper

import pl.hypeapp.dataproviders.entity.room.WatchedSeasonCountEntity
import pl.hypeapp.domain.model.WatchedEpisodesCountModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WatchedSeasonCountModelMapper @Inject constructor()
    : EntityMapper<WatchedEpisodesCountModel, WatchedSeasonCountEntity>() {

    override fun transform(entity: WatchedSeasonCountEntity?): WatchedEpisodesCountModel {
        entity!!.let {
            return WatchedEpisodesCountModel(
                    id = it.seasonId,
                    count = it.count
            )
        }
    }

}
