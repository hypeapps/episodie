package pl.hypeapp.domain.usecase.base

import io.reactivex.observers.DisposableSingleObserver

open class DefaultSingleObserver<T> : DisposableSingleObserver<T>() {

    override fun onSuccess(model: T) {}

    override fun onError(error: Throwable) {}

}
