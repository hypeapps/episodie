package pl.hypeapp.presentation.mainfeed

import pl.hypeapp.domain.model.MostPopularModel
import pl.hypeapp.domain.usecase.base.DefaultSingleObserver
import pl.hypeapp.presentation.base.Presenter
import javax.inject.Inject

class MainFeedPresenter @Inject constructor() : Presenter<MainFeedView>() {

    override fun onAttachView(view: MainFeedView) {
        super.onAttachView(view)
        this.view?.addFabButtonLandscapePadding()
//        tvShowUseCase.map(TvShowObserver(), GetTvShowUseCase.Params.createParams("82"))
    }

    override fun onDetachView() {
        super.onDetachView()
//        tvShowUseCase.dispose()
    }

    inner class TvShowObserver : DefaultSingleObserver<MostPopularModel>() {
        override fun onSuccess(t: MostPopularModel) {
//            this@MainFeedPresenter.view?.getAllSeasons(t.name)
        }

        override fun onError(e: Throwable) {
//            this@MainFeedPresenter.view?.getAllSeasons("${e.printStackTrace()}")
        }
    }

}
