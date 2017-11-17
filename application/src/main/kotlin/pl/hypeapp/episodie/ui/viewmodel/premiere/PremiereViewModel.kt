package pl.hypeapp.episodie.ui.viewmodel.premiere

import pl.hypeapp.domain.model.PremiereDateModel
import pl.hypeapp.episodie.ui.base.adapter.ViewType

class PremiereViewModel(val premiereDateModel: PremiereDateModel,
                        var isFirstItem: Boolean = false,
                        var isLastItem: Boolean = false) : ViewType {

    override fun getViewType(): Int = ViewType.ITEM
}
