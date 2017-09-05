package pl.hypeapp.dataproviders.entity.mapper

import pl.hypeapp.dataproviders.entity.room.WatchedEpisodesCountEntity
import pl.hypeapp.domain.model.WatchedEpisodesCountModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WatchedEpisodesCountModelMapper @Inject constructor()
    : EntityMapper<WatchedEpisodesCountModel, WatchedEpisodesCountEntity>() {

    override fun transform(entity: WatchedEpisodesCountEntity?): WatchedEpisodesCountModel {
        entity!!.let {
            return WatchedEpisodesCountModel(
                    id = it.tvShowId,
                    count = it.count)
        }
    }

}
