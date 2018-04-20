package pl.hypeapp.presentation.mainfeed

import pl.hypeapp.presentation.base.Presenter
import javax.inject.Inject

class MainFeedPresenter @Inject constructor() : Presenter<MainFeedView>() {

    override fun onAttachView(view: MainFeedView) {
        super.onAttachView(view)
        this.view?.addFabButtonLandscapePadding()
        this.view?.initToolbar()
        this.view?.initPagerAdapter()
    }
}
