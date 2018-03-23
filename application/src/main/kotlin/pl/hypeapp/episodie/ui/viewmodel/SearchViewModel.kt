package pl.hypeapp.episodie.ui.viewmodel

import android.arch.lifecycle.ViewModel
import pl.hypeapp.domain.model.search.BasicSearchResultModel

class SearchViewModel : ViewModel() {

    var searchSuggestions: List<BasicSearchResultViewModel> = ArrayList()

    fun retainModel(suggestions: List<BasicSearchResultModel>) {
        this.searchSuggestions = suggestions.map {
            BasicSearchResultViewModel(it)
        }
    }

    fun clearAndRetainModel(suggestions: List<BasicSearchResultModel>) {
        this.searchSuggestions = ArrayList()
        retainModel(suggestions)
    }

    inline fun loadModel(populateRecyclerList: () -> Unit) {
        if (!searchSuggestions.isEmpty()) populateRecyclerList()
    }

}
