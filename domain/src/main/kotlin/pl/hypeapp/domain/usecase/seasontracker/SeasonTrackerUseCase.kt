package pl.hypeapp.domain.usecase.seasontracker

import io.reactivex.Single
import pl.hypeapp.domain.executor.PostExecutionThread
import pl.hypeapp.domain.executor.ThreadExecutor
import pl.hypeapp.domain.model.collections.SeasonTrackerModel
import pl.hypeapp.domain.repository.SeasonTrackerRepository
import pl.hypeapp.domain.usecase.base.AbsRxSingleUseCase
import javax.inject.Inject

class SeasonTrackerUseCase @Inject constructor(threadExecutor: ThreadExecutor,
                                               postExecutionThread: PostExecutionThread,
                                               private val repository: SeasonTrackerRepository)
    : AbsRxSingleUseCase<SeasonTrackerModel, Void?>(threadExecutor, postExecutionThread) {

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun createSingle(unused: Void?): Single<SeasonTrackerModel> {
        return repository.getSeasonTackerModel()
    }

}
