package pl.hypeapp.domain.usecase.base

import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver


abstract class AbsRxSingleUseCase<T, Params> protected constructor(private val threadExecutor: Scheduler,
                                                                   private val postExecutionThread: Scheduler) {

    private val disposables: CompositeDisposable = CompositeDisposable()

    internal abstract fun createSingle(params: Params): Single<T>

    fun execute(observer: DisposableSingleObserver<T>, params: Params) {
        addDisposable(this.createSingle(params)
                .subscribeOn(threadExecutor)
                .observeOn(postExecutionThread)
                .subscribeWith(observer))
    }

    fun execute(params: Params): Single<T> {
        return createSingle(params)
    }

    fun dispose() {
        if (!disposables.isDisposed) {
            disposables.dispose()
        }
    }

    private fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }
}
