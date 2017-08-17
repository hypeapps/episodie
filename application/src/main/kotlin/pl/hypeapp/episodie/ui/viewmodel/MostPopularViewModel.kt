package pl.hypeapp.episodie.ui.viewmodel

import android.arch.lifecycle.ViewModel
import pl.hypeapp.domain.model.MostPopularModel

class MostPopularViewModel : ViewModel() {

    var page: Int = 0

    var retainedModel: MostPopularModel? = null

    var tvShowList: ArrayList<TvShowViewModel> = ArrayList()

    fun retainModel(model: MostPopularModel) {
        retainedModel = model
        tvShowList.addAll(model.tvShows.map { TvShowViewModel(it) })
        page = model.pageableRequest.page
    }

    fun clearModel() {
        retainedModel = null
        tvShowList.clear()
        page = 0
    }

    fun clearAndRetainModel(model: MostPopularModel) {
        clearModel()
        retainModel(model)
    }

    inline fun loadModel(requestMostPopular: () -> Unit, populateRecyclerList: () -> Unit) {
        if (retainedModel == null)
            requestMostPopular()
        else
            populateRecyclerList()
    }

}
