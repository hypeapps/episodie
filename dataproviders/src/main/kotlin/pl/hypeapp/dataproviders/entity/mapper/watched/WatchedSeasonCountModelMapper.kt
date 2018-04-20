package pl.hypeapp.dataproviders.entity.mapper.watched

import pl.hypeapp.dataproviders.entity.mapper.Mapper
import pl.hypeapp.dataproviders.entity.room.WatchedSeasonCountEntity
import pl.hypeapp.domain.model.watched.WatchedEpisodesCountModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WatchedSeasonCountModelMapper @Inject constructor()
    : Mapper<WatchedEpisodesCountModel, WatchedSeasonCountEntity>() {

    override fun transform(item: WatchedSeasonCountEntity?): WatchedEpisodesCountModel {
        item!!.let {
            return WatchedEpisodesCountModel(
                    id = it.seasonId,
                    count = it.count
            )
        }
    }

}
