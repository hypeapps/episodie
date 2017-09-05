package pl.hypeapp.domain.usecase.base

import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import pl.hypeapp.domain.executor.PostExecutionThread
import pl.hypeapp.domain.executor.ThreadExecutor

abstract class AbsRxCompletableUseCase<Params> protected constructor(
        private val threadExecutor: ThreadExecutor,
        private val postExecutionThread: PostExecutionThread) {

    private val disposables: CompositeDisposable = CompositeDisposable()

    internal abstract fun createCompletable(params: Params): Completable

    fun execute(observer: DefaultCompletableObserver, params: Params) {
        addDisposable(this.createCompletable(params)
                .subscribeOn(threadExecutor.getScheduler())
                .observeOn(postExecutionThread.getScheduler())
                .subscribeWith(observer))
    }

    internal fun execute(params: Params): Completable {
        return createCompletable(params)
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
