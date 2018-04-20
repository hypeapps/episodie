package pl.hypeapp.dataproviders.entity.mapper.watched

import pl.hypeapp.dataproviders.entity.mapper.Mapper
import pl.hypeapp.dataproviders.entity.room.WatchedEpisodesCountEntity
import pl.hypeapp.domain.model.watched.WatchedEpisodesCountModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WatchedEpisodesCountModelMapper @Inject constructor()
    : Mapper<WatchedEpisodesCountModel, WatchedEpisodesCountEntity>() {

    override fun transform(item: WatchedEpisodesCountEntity?): WatchedEpisodesCountModel {
        item!!.let {
            return WatchedEpisodesCountModel(
                    id = it.tvShowId,
                    count = it.count)
        }
    }

}
