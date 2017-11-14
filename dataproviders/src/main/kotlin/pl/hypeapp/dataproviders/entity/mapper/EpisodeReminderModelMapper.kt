package pl.hypeapp.dataproviders.entity.mapper

import pl.hypeapp.dataproviders.entity.room.EpisodeReminderEntity
import pl.hypeapp.domain.model.EpisodeReminderModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EpisodeReminderModelMapper @Inject constructor() : EntityMapper<EpisodeReminderModel, EpisodeReminderEntity>() {

    override fun transform(entity: EpisodeReminderEntity?): EpisodeReminderModel? {
        return EpisodeReminderModel(episodeId = entity?.episodeId!!,
                tvShowId = entity.tvShowId,
                seasonId = entity.seasonId,
                name = entity.name,
                episodeNumber = entity.episodeNumber,
                jobId = entity.jobId,
                timestamp = entity.premiereDate,
                tvShowName = entity.tvShowName)
    }
}
