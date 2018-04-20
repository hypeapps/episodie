package pl.hypeapp.dataproviders.entity.mapper.reminder

import pl.hypeapp.dataproviders.entity.mapper.Mapper
import pl.hypeapp.dataproviders.entity.room.EpisodeReminderEntity
import pl.hypeapp.domain.model.reminder.EpisodeReminderModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EpisodeReminderModelMapper @Inject constructor() : Mapper<EpisodeReminderModel, EpisodeReminderEntity>() {

    override fun transform(item: EpisodeReminderEntity?): EpisodeReminderModel? {
        return EpisodeReminderModel(episodeId = item?.episodeId!!,
                tvShowId = item.tvShowId,
                seasonId = item.seasonId,
                name = item.name,
                episodeNumber = item.episodeNumber,
                jobId = item.jobId,
                timestamp = item.premiereDate,
                tvShowName = item.tvShowName)
    }
}
