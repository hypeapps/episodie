package pl.hypeapp.episodie.ui.features.seasons.adapter

import android.widget.ImageView
import pl.hypeapp.domain.model.EpisodeModel
import pl.hypeapp.domain.model.SeasonModel

interface OnChangeWatchStateListener {

    fun onChangeEpisodeWatchState(episodeModel: EpisodeModel, view: ImageView)

    fun onChangeSeasonWatchState(seasonModel: SeasonModel, view: ImageView)

}
