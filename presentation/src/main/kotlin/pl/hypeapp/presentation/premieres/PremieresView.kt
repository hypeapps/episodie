package pl.hypeapp.presentation.premieres

import pl.hypeapp.domain.model.PremiereDatesModel
import pl.hypeapp.domain.model.TvShowModel
import pl.hypeapp.presentation.base.View

interface PremieresView : View {

    fun initSwipeRefreshLayout()

    fun initRecyclerAdapter()

    fun loadBackdrop()

    fun showError()

    fun loadViewModel()

    fun populateRecyclerList(premiereDatesModel: PremiereDatesModel?)
    fun cancelNotification(jobId: Int): Boolean
    fun observeDragDrawer()
    fun animateDrawerHamburgerArrow(progress: Float)
    fun navigateToTvShowDetails(tvShowModel: TvShowModel)
    fun showErrorToast()
}
