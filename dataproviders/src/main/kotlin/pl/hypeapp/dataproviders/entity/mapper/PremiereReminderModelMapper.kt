package pl.hypeapp.dataproviders.entity.mapper

import pl.hypeapp.dataproviders.entity.room.PremiereReminderEntity
import pl.hypeapp.domain.model.PremiereReminderModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PremiereReminderModelMapper @Inject constructor() : EntityMapper<PremiereReminderEntity, PremiereReminderModel>() {

    override fun transform(entity: PremiereReminderModel?): PremiereReminderEntity {
        return PremiereReminderEntity(entity?.tvShowId!!, entity.jobId!!, entity.timestamp!!)
    }
}
