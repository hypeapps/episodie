package pl.hypeapp.episodie.ui.viewmodel

import android.arch.lifecycle.ViewModel
import pl.hypeapp.domain.model.watched.WatchedTvShowModel

class YourLibraryViewModel : ViewModel() {

    var page: Int = 0

    var isError: Boolean = false

    var isLastPage: Boolean = false

    var watchedShows: ArrayList<WatchedTvShowViewModel> = ArrayList()

    fun retainModel(model: List<WatchedTvShowModel>) {
        watchedShows.addAll(model.map {
            WatchedTvShowViewModel(it.tvShowModel,
                    watchedEpisodes = it.watchedEpisodesCount,
                    watchedSeasons = it.watchedSeasonsCount,
                    watchingTime = it.runtime ?: 0)
        }.toList())
    }

    fun clearAndRetainModel(model: List<WatchedTvShowModel>) {
        this.watchedShows = ArrayList()
        retainModel(model)
    }

    inline fun loadModel(populateRecyclerList: () -> Unit, requestModel: () -> Unit) {
        if (watchedShows.isNotEmpty()) {
            populateRecyclerList()
        } else {
            requestModel()
        }
    }

}
