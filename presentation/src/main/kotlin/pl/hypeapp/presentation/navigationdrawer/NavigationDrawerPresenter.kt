package pl.hypeapp.presentation.navigationdrawer

import pl.hypeapp.domain.usecase.base.DefaultFlowableObserver
import pl.hypeapp.domain.usecase.userstats.UserRuntimeFlowableUseCase
import pl.hypeapp.presentation.base.Presenter
import javax.inject.Inject

class NavigationDrawerPresenter @Inject constructor(private val userRuntimeUseCase: UserRuntimeFlowableUseCase)
    : Presenter<NavigationDrawerView>() {

    override fun onAttachView(view: NavigationDrawerView) {
        super.onAttachView(view)
        updateWatchingTime()
    }

    override fun onDetachView() {
        super.onDetachView()
        userRuntimeUseCase.dispose()
    }

    fun updateWatchingTime() = userRuntimeUseCase.execute(UserRuntimeObserver(), null)

    inner class UserRuntimeObserver : DefaultFlowableObserver<Long>() {
        override fun onNext(t: Long) {
            this@NavigationDrawerPresenter.view?.setWatchingTime(t)
        }
    }
}
