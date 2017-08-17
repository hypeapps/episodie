package pl.hypeapp.presentation.tvshowdetails

import pl.hypeapp.domain.model.TvShowModel
import pl.hypeapp.presentation.base.Presenter
import javax.inject.Inject

class TvShowDetailsPresenter @Inject constructor() : Presenter<TvShowDetailsView>() {

    override fun onAttachView(view: TvShowDetailsView) {
        super.onAttachView(view)
        val model = this.view?.getModel()
        this.view?.setNavigationBarOptions()
        this.view?.initPagerAdapter(model)
        filTvShowDetailsInfo(model)
    }

    override fun onDetachView() {
        super.onDetachView()
    }

    fun onPageSelected(position: Int) {
        if (position <= 0) {
            view?.expandAppBar()
        }
    }

    fun onPagerAdapterInit() {
        view?.startFabButtonAnimation()
    }

    fun onAddToWatched() {}

    private fun filTvShowDetailsInfo(tvShowModel: TvShowModel?) {
        this.view?.setCover(tvShowModel?.imageMedium)
        this.view?.setBackdrop(backdropUrl = tvShowModel?.imageOriginal, placeholderUrl = tvShowModel?.imageMedium)
        this.view?.setTitle(tvShowModel?.name)
        this.view?.setFullRuntime(tvShowModel?.fullRuntime)
        this.view?.setPremiered(tvShowModel?.premiered)
        this.view?.setStatus(tvShowModel?.status)
    }

}
