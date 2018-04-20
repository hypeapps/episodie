package pl.hypeapp.presentation.premieres

import pl.hypeapp.domain.model.premiere.PremiereDateModel
import pl.hypeapp.domain.model.premiere.PremiereDatesModel
import pl.hypeapp.domain.model.reminder.PremiereReminderModel
import pl.hypeapp.domain.model.tvshow.TvShowModel
import pl.hypeapp.domain.usecase.base.DefaultCompletableObserver
import pl.hypeapp.domain.usecase.base.DefaultSingleObserver
import pl.hypeapp.domain.usecase.gettvshow.GetTvShowUseCase
import pl.hypeapp.domain.usecase.premieres.AddPremiereReminderUseCase
import pl.hypeapp.domain.usecase.premieres.DeletePremiereReminderUseCase
import pl.hypeapp.domain.usecase.premieres.GetPremiereReminderUseCase
import pl.hypeapp.domain.usecase.premieres.PremiereDatesUseCase
import pl.hypeapp.presentation.base.Presenter
import javax.inject.Inject

class PremieresPresenter @Inject constructor(private val premiereDatesUseCase: PremiereDatesUseCase,
                                             private val addPremiereReminderUseCase: AddPremiereReminderUseCase,
                                             private val getPremiereReminderUseCase: GetPremiereReminderUseCase,
                                             private val deletePremiereReminderUseCase: DeletePremiereReminderUseCase,
                                             private val getTvShowUseCase: GetTvShowUseCase)
    : Presenter<PremieresView>() {

    companion object {
        const val SIZE_PAGE = 10
    }

    override fun onAttachView(view: PremieresView) {
        super.onAttachView(view)
        this.view?.initSwipeRefreshLayout()
        this.view?.initRecyclerAdapter()
        this.view?.loadBackdrop()
        this.view?.loadViewModel()
    }

    override fun onDetachView() {
        super.onDetachView()
        premiereDatesUseCase.dispose()
        addPremiereReminderUseCase.dispose()
        getPremiereReminderUseCase.dispose()
        deletePremiereReminderUseCase.dispose()
        getTvShowUseCase.dispose()
    }

    fun onNotificationSchedule(premiereReminderModel: PremiereReminderModel) {
        addPremiereReminderUseCase.execute(DefaultCompletableObserver(),
                AddPremiereReminderUseCase.Params.createQuery(premiereReminderModel))
    }

    fun onNotificationDismiss(tvShowId: String) {
        deletePremiereReminderUseCase.execute(DeletePremiereReminderObserver(),
                DeletePremiereReminderUseCase.Params.createQuery(tvShowId))
    }

    fun requestPremiereDates(page: Int, update: Boolean) {
        premiereDatesUseCase.execute(PremiereDatesObserver(), PremiereDatesUseCase.Params.createQuery(page, SIZE_PAGE, update))
    }

    fun onDrawerDrag(progress: Float) = this.view?.animateDrawerHamburgerArrow(progress)

    fun onPremiereItemClick(model: PremiereDateModel) {
        getTvShowUseCase.execute(GetTvShowObserver(), GetTvShowUseCase.Params.createParams(model.id!!, false))
    }

    inner class PremiereDatesObserver : DefaultSingleObserver<PremiereDatesModel>() {
        override fun onSuccess(model: PremiereDatesModel) {
            this@PremieresPresenter.view?.populateRecyclerList(model)
        }

        override fun onError(error: Throwable) {
            this@PremieresPresenter.view?.showError()
        }
    }

    inner class DeletePremiereReminderObserver : DefaultSingleObserver<PremiereReminderModel>() {
        override fun onSuccess(model: PremiereReminderModel) {
            model.jobId?.let {
                this@PremieresPresenter.view?.cancelNotification(it)
            }
        }
    }

    inner class GetTvShowObserver : DefaultSingleObserver<TvShowModel>() {
        override fun onSuccess(model: TvShowModel) {
            this@PremieresPresenter.view?.navigateToTvShowDetails(model)
        }

        override fun onError(error: Throwable) {
            this@PremieresPresenter.view?.showErrorToast()
        }
    }

}
