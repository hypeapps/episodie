package pl.hypeapp.domain.usecase.runtime

import io.reactivex.Single
import pl.hypeapp.domain.executor.PostExecutionThread
import pl.hypeapp.domain.executor.ThreadExecutor
import pl.hypeapp.domain.repository.RuntimeRepository
import pl.hypeapp.domain.usecase.base.AbsRxSingleUseCase
import javax.inject.Inject

class UserRuntimeUseCase @Inject constructor(threadExecutor: ThreadExecutor,
                                             postExecutionThread: PostExecutionThread,
                                             private val repository: RuntimeRepository)
    : AbsRxSingleUseCase<Long, Void?>(threadExecutor, postExecutionThread) {

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun createSingle(unused: Void?): Single<Long> {
        return repository.getUserFullRuntime()
    }

}
