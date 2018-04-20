package pl.hypeapp.episodie.ui.viewmodel.premiere

import android.arch.lifecycle.ViewModel
import pl.hypeapp.domain.model.premiere.PremiereDatesModel

class PremieresViewModel : ViewModel() {

    var page: Int = 0

    var isLast: Boolean = false

    var retainedModel: PremiereDatesModel? = null

    var premiereList: ArrayList<PremiereViewModel> = ArrayList()

    fun retainModel(model: PremiereDatesModel) {
        retainedModel = model
        premiereList
                .addAll(model.premiereDates!!
                .map { PremiereViewModel(it) })
        premiereList = ArrayList(premiereList.distinctBy { it.premiereDateModel.id })
        page = model.pageableRequest.page
        isLast = model.pageableRequest.last
    }

    fun clearModel() {
        retainedModel = null
        premiereList.clear()
        page = 0
        isLast = false
    }

    fun clearAndRetainModel(model: PremiereDatesModel) {
        clearModel()
        retainModel(model)
    }

    inline fun loadModel(requestPremiereDates: () -> Unit, populateRecyclerList: () -> Unit) {
        if (retainedModel == null)
            requestPremiereDates()
        else
            populateRecyclerList()
    }

}
