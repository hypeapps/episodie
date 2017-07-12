package pl.hypeapp.presentation.base

open class Presenter<V : View> {

    var view: V? = null

    open fun onAttachView(view: V) {
        this.view = view
    }

    open fun onDetachView() {
        this.view = null
    }

}