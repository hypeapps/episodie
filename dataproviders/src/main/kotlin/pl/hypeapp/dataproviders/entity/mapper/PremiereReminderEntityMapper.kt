package pl.hypeapp.dataproviders.entity.mapper

import pl.hypeapp.dataproviders.entity.room.PremiereReminderEntity
import pl.hypeapp.domain.model.PremiereReminderModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PremiereReminderEntityMapper @Inject constructor() : EntityMapper<PremiereReminderModel, PremiereReminderEntity>() {

    override fun transform(entity: PremiereReminderEntity?): PremiereReminderModel {
        return PremiereReminderModel(entity?.tvShowId, entity?.jobId, entity?.timestamp)
    }
}
