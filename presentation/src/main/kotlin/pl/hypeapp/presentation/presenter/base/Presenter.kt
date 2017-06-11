package pl.hypeapp.presentation.presenter.base

import pl.hypeapp.presentation.view.base.View

open class Presenter<V : View> {

    var view: V? = null

    open fun onAttachView(view: V) {
        this.view = view
    }

    open fun onDetachView() {
        this.view = null
    }
}