package pl.hypeapp.dataproviders.entity.mapper.reminder

import pl.hypeapp.dataproviders.entity.mapper.Mapper
import pl.hypeapp.dataproviders.entity.room.PremiereReminderEntity
import pl.hypeapp.domain.model.reminder.PremiereReminderModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PremiereReminderEntityMapper @Inject constructor() : Mapper<PremiereReminderModel, PremiereReminderEntity>() {

    override fun transform(item: PremiereReminderEntity?): PremiereReminderModel {
        return PremiereReminderModel(item?.tvShowId, item?.jobId, item?.timestamp)
    }
}
