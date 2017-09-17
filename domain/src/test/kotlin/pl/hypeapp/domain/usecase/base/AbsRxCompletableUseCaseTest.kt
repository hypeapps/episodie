package pl.hypeapp.domain.usecase.base

import com.nhaarman.mockito_kotlin.given
import io.reactivex.Completable
import io.reactivex.CompletableOnSubscribe
import io.reactivex.schedulers.TestScheduler
import org.amshove.kluent.`should equal`
import org.amshove.kluent.mock
import org.junit.Before
import org.junit.Test
import pl.hypeapp.domain.executor.PostExecutionThread
import pl.hypeapp.domain.executor.ThreadExecutor

class AbsRxCompletableUseCaseTest {

    private val threadExecutor: ThreadExecutor = mock()

    private val postExecutionThread: PostExecutionThread = mock()

    private lateinit var observer: DisposableCompletableObserverTest

    private lateinit var absRxCompletableUseCase: AbsRxCompletableUseCase<AbsRxCompletableUseCaseTest.Params>

    @Before
    fun setUp() {
        absRxCompletableUseCase = AbsRxCompletableUseCaseTestClass(threadExecutor, postExecutionThread)
        observer = DisposableCompletableObserverTest()
        given(threadExecutor.getScheduler()).willReturn(TestScheduler())
        given(postExecutionThread.getScheduler()).willReturn(TestScheduler())
    }

    @Test
    fun `should dispose observer after execution`() {
        absRxCompletableUseCase.execute(observer, Params.EMPTY)
        absRxCompletableUseCase.dispose()

        observer.isDisposed `should equal` true
    }

    private class AbsRxCompletableUseCaseTestClass(thread: ThreadExecutor,
                                                   postExecutionThread: PostExecutionThread)
        : AbsRxCompletableUseCase<AbsRxCompletableUseCaseTest.Params>(thread, postExecutionThread) {

        override fun createCompletable(params: Params): Completable = Completable.create(CompletableOnSubscribe { })
    }

    class DisposableCompletableObserverTest : DefaultCompletableObserver()

    private class Params private constructor() {
        companion object {
            val EMPTY = Params()
        }
    }

}
