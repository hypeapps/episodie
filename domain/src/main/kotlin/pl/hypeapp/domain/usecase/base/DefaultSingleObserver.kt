package pl.hypeapp.domain.usecase.base

import io.reactivex.observers.DisposableSingleObserver


open class DefaultSingleObserver<T> : DisposableSingleObserver<T>() {

    override fun onSuccess(t: T) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onError(e: Throwable) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}