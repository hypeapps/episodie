package pl.hypeapp.episodie.ui.viewmodel

import pl.hypeapp.domain.model.search.BasicSearchResultModel
import pl.hypeapp.episodie.ui.base.adapter.ViewType

class BasicSearchResultViewModel(val basicSearchResultModel: BasicSearchResultModel) : ViewType {

    override fun getViewType(): Int = ViewType.ITEM

}
