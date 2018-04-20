package pl.hypeapp.episodie.ui.viewmodel

import android.arch.lifecycle.ViewModel
import pl.hypeapp.domain.model.tvshow.TvShowModel

class SearchViewModel : ViewModel() {

    var searchSuggestions: List<TvShowViewModel> = ArrayList()

    fun retainModel(suggestions: List<TvShowModel>) {
        this.searchSuggestions = suggestions.map {
            TvShowViewModel(it)
        }
    }

    fun clearAndRetainModel(suggestions: List<TvShowModel>) {
        this.searchSuggestions = ArrayList()
        retainModel(suggestions)
    }

    inline fun loadModel(populateRecyclerList: () -> Unit) {
        if (!searchSuggestions.isEmpty()) populateRecyclerList()
    }

}
