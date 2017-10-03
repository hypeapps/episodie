package pl.hypeapp.dataproviders.entity.mapper

import pl.hypeapp.dataproviders.entity.api.BasicSearchResultEntity
import pl.hypeapp.domain.model.BasicSearchResultModel
import javax.inject.Inject

class BasicSearchEntityMapper @Inject constructor() : EntityMapper<BasicSearchResultModel, BasicSearchResultEntity>() {

    override fun transform(entity: BasicSearchResultEntity?): BasicSearchResultModel? {
        return BasicSearchResultModel(id = entity?.tvShowApiId,
                name = entity?.name,
                runtime = entity?.fullRuntime,
                episodeOrder = entity?.episodeOrder,
                imageMedium = entity?.imageMedium,
                imageOriginal = entity?.imageOriginal)
    }

}
