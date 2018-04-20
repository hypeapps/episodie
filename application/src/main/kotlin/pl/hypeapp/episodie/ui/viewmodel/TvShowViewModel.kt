package pl.hypeapp.episodie.ui.viewmodel

import pl.hypeapp.domain.model.tvshow.TvShowModel
import pl.hypeapp.episodie.ui.base.adapter.ViewType

open class TvShowViewModel(val tvShow: TvShowModel?) : ViewType {

    override fun getViewType(): Int = ViewType.ITEM

}
