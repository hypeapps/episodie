package pl.hypeapp.domain.usecase.base

import io.reactivex.subscribers.DisposableSubscriber

open class DefaultFlowableObserver<T> : DisposableSubscriber<T>() {

    override fun onError(t: Throwable?) {}

    override fun onNext(t: T) {}

    override fun onComplete() {}

}
