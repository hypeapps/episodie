package pl.hypeapp.domain.usecase.base

import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subscribers.DisposableSubscriber
import pl.hypeapp.domain.executor.PostExecutionThread
import pl.hypeapp.domain.executor.ThreadExecutor

abstract class AbsRxFlowableUseCase<T, Params> protected constructor(
        private val threadExecutor: ThreadExecutor,
        private val postExecutionThread: PostExecutionThread) {

    private val disposables: CompositeDisposable = CompositeDisposable()

    internal abstract fun createFlowable(params: Params): Flowable<T>

    fun execute(observer: DisposableSubscriber<T>, params: Params) {
        addDisposable(this.createFlowable(params)
                .subscribeOn(threadExecutor.getScheduler())
                .observeOn(postExecutionThread.getScheduler())
                .subscribeWith(observer))
    }

    internal fun execute(params: Params): Flowable<T> {
        return createFlowable(params)
    }

    fun dispose() {
        if (!disposables.isDisposed) {
            disposables.dispose()
        }
    }

    private fun addDisposable(disposable: DisposableSubscriber<T>) {
        disposables.add(disposable)
    }
}

