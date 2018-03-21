package pl.hypeapp.domain.usecase.userstats

import io.reactivex.Single
import pl.hypeapp.domain.executor.PostExecutionThread
import pl.hypeapp.domain.executor.ThreadExecutor
import pl.hypeapp.domain.model.watched.UserStatsModel
import pl.hypeapp.domain.repository.UserStatsRepository
import pl.hypeapp.domain.usecase.base.AbsRxSingleUseCase
import javax.inject.Inject

class UserStatsUseCase @Inject constructor(threadExecutor: ThreadExecutor,
                                           postExecutionThread: PostExecutionThread,
                                           private val repository: UserStatsRepository)
    : AbsRxSingleUseCase<UserStatsModel, Void?>(threadExecutor, postExecutionThread) {

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun createSingle(unused: Void?): Single<UserStatsModel> {
        return repository.getUserStats().doOnSuccess {
            if (it.watchingTime == 0L) {
                throw Throwable("No watching time persisted")
            }
        }
    }
}
