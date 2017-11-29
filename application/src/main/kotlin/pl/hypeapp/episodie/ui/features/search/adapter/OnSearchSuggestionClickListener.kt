package pl.hypeapp.episodie.ui.features.search.adapter

import android.view.View
import pl.hypeapp.episodie.ui.viewmodel.BasicSearchResultViewModel

interface OnSearchSuggestionClickListener {

    fun onItemClick(basicSearchResultViewModel: BasicSearchResultViewModel, view: View)

}
