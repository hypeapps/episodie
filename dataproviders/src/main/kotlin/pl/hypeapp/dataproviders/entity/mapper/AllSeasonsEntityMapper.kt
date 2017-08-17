package pl.hypeapp.dataproviders.entity.mapper

import pl.hypeapp.dataproviders.entity.AllSeasonsEntity
import pl.hypeapp.domain.model.AllSeasonsModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AllSeasonsEntityMapper @Inject constructor(val seasonEntityMapper: SeasonEntityMapper) : EntityMapper<AllSeasonsModel, AllSeasonsEntity>() {

    override fun transform(entity: AllSeasonsEntity?): AllSeasonsModel {
        return AllSeasonsModel(seasons = seasonEntityMapper.transform(entity?.seasons!!))
    }

}
