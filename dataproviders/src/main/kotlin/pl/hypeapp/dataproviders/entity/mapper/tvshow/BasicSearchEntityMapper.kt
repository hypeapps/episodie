package pl.hypeapp.dataproviders.entity.mapper.tvshow

import pl.hypeapp.dataproviders.entity.api.BasicSearchResultEntity
import pl.hypeapp.dataproviders.entity.mapper.Mapper
import pl.hypeapp.domain.model.search.BasicSearchResultModel
import javax.inject.Inject

class BasicSearchEntityMapper @Inject constructor() : Mapper<BasicSearchResultModel, BasicSearchResultEntity>() {

    override fun transform(item: BasicSearchResultEntity?): BasicSearchResultModel? {
        return BasicSearchResultModel(id = item?.tvShowApiId,
                name = item?.name,
                runtime = item?.fullRuntime,
                episodeOrder = item?.episodeOrder,
                imageMedium = item?.imageMedium,
                imageOriginal = item?.imageOriginal)
    }

}
