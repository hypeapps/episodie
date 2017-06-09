package pl.hypeapp.presentation.presenter.base

import pl.hypeapp.presentation.view.base.BaseView

abstract class Presenter<V : BaseView> {

    var view: V? = null

    open fun onCreate(view: V) {
        this.view = view
    }

    abstract fun onResume()

    abstract fun onPause()

    abstract fun onDestroy()
}