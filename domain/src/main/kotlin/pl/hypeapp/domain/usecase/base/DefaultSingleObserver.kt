package pl.hypeapp.domain.usecase.base

import io.reactivex.observers.DisposableSingleObserver

open class DefaultSingleObserver<T> : DisposableSingleObserver<T>() {

    override fun onSuccess(model: T) {
        TODO("not implemented")
    }

    override fun onError(error: Throwable) {
        TODO("not implemented")
    }
}