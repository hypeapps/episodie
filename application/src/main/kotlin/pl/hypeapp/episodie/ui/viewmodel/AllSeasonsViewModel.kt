package pl.hypeapp.episodie.ui.viewmodel

import android.arch.lifecycle.ViewModel
import pl.hypeapp.domain.model.AllSeasonsModel
import pl.hypeapp.domain.model.SeasonModel

class AllSeasonsViewModel : ViewModel() {

    var retainedModel: AllSeasonsModel? = null

    var seasonsList: ArrayList<SeasonModel> = ArrayList()

    fun retainModel(model: AllSeasonsModel) {
        retainedModel = model
        model.seasons?.let {
            seasonsList.addAll(it)
        }
    }

    fun clearModel() {
        retainedModel = null
        seasonsList.clear()
    }

    fun clearAndRetainModel(model: AllSeasonsModel) {
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
