package pl.hypeapp.domain.usecase.userstats

import io.reactivex.Single
import pl.hypeapp.domain.executor.PostExecutionThread
import pl.hypeapp.domain.executor.ThreadExecutor
import pl.hypeapp.domain.repository.UserStatsRepository
import pl.hypeapp.domain.usecase.base.AbsRxSingleUseCase
import javax.inject.Inject

class UserRuntimeUseCase @Inject constructor(threadExecutor: ThreadExecutor,
                                             postExecutionThread: PostExecutionThread,
                                             private val repository: UserStatsRepository)
    : AbsRxSingleUseCase<Long, Void?>(threadExecutor, postExecutionThread) {

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun createSingle(unused: Void?): Single<Long> {
        return repository.getUserFullRuntime()
    }

}
