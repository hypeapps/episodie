package pl.hypeapp.episodie.ui.features.search.adapter

import android.view.View
import android.widget.ImageView
import pl.hypeapp.domain.model.tvshow.TvShowModel

interface OnSearchSuggestionClickListener {

    fun onItemClick(tvShowModel: TvShowModel, view: View)

    fun onChangeWatchState(tvShowModel: TvShowModel, icon: ImageView)

}
