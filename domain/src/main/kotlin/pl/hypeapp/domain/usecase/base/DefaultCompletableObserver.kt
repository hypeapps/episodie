package pl.hypeapp.domain.usecase.base

import io.reactivex.observers.DisposableCompletableObserver

open class DefaultCompletableObserver : DisposableCompletableObserver() {

    override fun onComplete() {}

    override fun onError(e: Throwable) {}

}
