package pl.hypeapp.dataproviders.entity.mapper.reminder

import pl.hypeapp.dataproviders.entity.mapper.Mapper
import pl.hypeapp.dataproviders.entity.room.PremiereReminderEntity
import pl.hypeapp.domain.model.reminder.PremiereReminderModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PremiereReminderModelMapper @Inject constructor() : Mapper<PremiereReminderEntity, PremiereReminderModel>() {

    override fun transform(item: PremiereReminderModel?): PremiereReminderEntity {
        return PremiereReminderEntity(item?.tvShowId!!, item.jobId!!, item.timestamp!!)
    }
}
