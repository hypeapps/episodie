package pl.hypeapp.episodie.ui.features.top.adapter

import android.widget.ImageView
import pl.hypeapp.domain.model.tvshow.TvShowModel
import pl.hypeapp.episodie.ui.base.adapter.ViewTypeDelegateAdapter

interface TopListOnViewSelectedListener : ViewTypeDelegateAdapter.OnViewSelectedListener {

    fun onWatchStateChange(tvShow: TvShowModel, diamondIcon: ImageView)

}
