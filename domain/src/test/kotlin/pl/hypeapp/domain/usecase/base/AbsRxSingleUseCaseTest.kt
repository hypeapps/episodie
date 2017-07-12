package pl.hypeapp.domain.usecase.base

import com.nhaarman.mockito_kotlin.given
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.amshove.kluent.`should equal`
import org.amshove.kluent.mock
import org.junit.Before
import org.junit.Test
import pl.hypeapp.domain.executor.PostExecutionThread
import pl.hypeapp.domain.executor.ThreadExecutor

class AbsRxSingleUseCaseTest {

    private val threadExecutor: ThreadExecutor = mock()

    private val postExecutionThread: PostExecutionThread = mock()

    private lateinit var observer: DisposableSingleObserverTest<Any>

    private lateinit var absRxSingleUseCase: AbsRxSingleUseCase<Any, Params>

    @Before
    fun setUp() {
        absRxSingleUseCase = AbsRxSingleUseCaseTestClass(threadExecutor, postExecutionThread)
        observer = DisposableSingleObserverTest()
        given(threadExecutor.getScheduler()).willReturn(TestScheduler())
        given(postExecutionThread.getScheduler()).willReturn(TestScheduler())
    }

    @Test
    fun `should dispose observer`() {
        absRxSingleUseCase.execute(observer, Params.EMPTY)
        absRxSingleUseCase.dispose()
        observer.isDisposed `should equal` true
    }

    private class AbsRxSingleUseCaseTestClass(thread: ThreadExecutor,
                                              postExecutionThread: PostExecutionThread)
        : AbsRxSingleUseCase<Any, Params>(thread, postExecutionThread) {

        override fun createSingle(params: Params): Single<Any> = Single.just(Any())

    }

    class DisposableSingleObserverTest<T> : DefaultSingleObserver<T>()

    private class Params private constructor() {
        companion object {
            val EMPTY = Params()
        }
    }

}
