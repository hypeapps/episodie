package pl.hypeapp.presentation.mostpopular

import pl.hypeapp.domain.model.MostPopularModel
import pl.hypeapp.presentation.base.View

interface MostPopularView : View {

    fun populateRecyclerList(mostPopularModel: MostPopularModel?)

    fun loadViewModel()

}
