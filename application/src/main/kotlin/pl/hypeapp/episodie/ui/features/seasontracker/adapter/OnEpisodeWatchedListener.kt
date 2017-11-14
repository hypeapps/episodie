package pl.hypeapp.episodie.ui.features.seasontracker.adapter

import android.view.View

interface OnEpisodeWatchedListener {

    fun onEpisodeSelect(episodeId: String, view: View)

    fun onEpisodeDeselect(episodeId: String, view: View)

}
