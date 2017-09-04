package pl.hypeapp.episodie.ui.features.top.adapter

import android.widget.ImageView
import pl.hypeapp.domain.model.TvShowModel
import pl.hypeapp.episodie.ui.base.adapter.ViewTypeDelegateAdapter

interface TopListOnViewSelectedListener : ViewTypeDelegateAdapter.OnViewSelectedListener {

    fun onChangeWatchState(tvShow: TvShowModel, diamondIcon: ImageView)

}
