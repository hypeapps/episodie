package pl.hypeapp.episodie.ui.features.seasons.adapter

import android.widget.ImageView
import pl.hypeapp.domain.model.tvshow.EpisodeModel
import pl.hypeapp.domain.model.tvshow.SeasonModel

interface OnChangeWatchStateListener {

    fun onChangeEpisodeWatchState(episodeModel: EpisodeModel, view: ImageView)

    fun onChangeSeasonWatchState(seasonModel: SeasonModel, view: ImageView)

}
