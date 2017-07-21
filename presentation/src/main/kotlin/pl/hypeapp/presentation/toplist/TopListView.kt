package pl.hypeapp.presentation.toplist

import pl.hypeapp.domain.model.TopListModel
import pl.hypeapp.presentation.base.View

interface TopListView : View {

    fun populateRecyclerList(topListModel: TopListModel?)

    fun loadViewModel()

}
