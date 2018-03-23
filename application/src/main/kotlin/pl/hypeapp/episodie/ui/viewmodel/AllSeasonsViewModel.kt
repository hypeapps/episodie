package pl.hypeapp.episodie.ui.viewmodel

import android.arch.lifecycle.ViewModel
import pl.hypeapp.domain.model.tvshow.SeasonModel
import pl.hypeapp.domain.model.tvshow.TvShowExtendedModel

class AllSeasonsViewModel : ViewModel() {

    var retainedModel: TvShowExtendedModel? = null

    var seasonsList: ArrayList<SeasonModel> = ArrayList()

    fun retainModel(model: TvShowExtendedModel) {
        retainedModel = model
        model.seasons?.let {
            seasonsList.addAll(it)
        }
    }

    fun clearModel() {
        retainedModel = null
        seasonsList.clear()
    }

    fun clearAndRetainModel(model: TvShowExtendedModel) {
        clearModel()
        retainModel(model)
    }

    inline fun loadModel(request: () -> Unit, populateRecyclerList: () -> Unit) {
        if (retainedModel == null)
            request()
        else
            populateRecyclerList()
    }

}
