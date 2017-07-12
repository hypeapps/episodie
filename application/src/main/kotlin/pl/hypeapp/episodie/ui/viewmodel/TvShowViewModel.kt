package pl.hypeapp.episodie.ui.viewmodel

import pl.hypeapp.domain.model.TvShowModel
import pl.hypeapp.episodie.adapter.ViewType

class TvShowViewModel(var tvShow: TvShowModel?) : ViewType {

    override fun getViewType(): Int = ViewType.ITEM

}
