package pl.hypeapp.domain.usecase.userstats

import io.reactivex.Flowable
import pl.hypeapp.domain.executor.PostExecutionThread
import pl.hypeapp.domain.executor.ThreadExecutor
import pl.hypeapp.domain.repository.UserStatsRepository
import pl.hypeapp.domain.usecase.base.AbsRxFlowableUseCase
import javax.inject.Inject

class UserRuntimeFlowableUseCase @Inject constructor(threadExecutor: ThreadExecutor,
                                                     postExecutionThread: PostExecutionThread,
                                                     private val repository: UserStatsRepository)
    : AbsRxFlowableUseCase<Long, Void?>(threadExecutor, postExecutionThread) {

    override fun createFlowable(params: Void?): Flowable<Long> {
        return repository.getUserFullRuntimeFlowable()
    }
}
