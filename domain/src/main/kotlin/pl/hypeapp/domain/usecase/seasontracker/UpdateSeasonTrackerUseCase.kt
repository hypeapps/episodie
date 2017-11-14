package pl.hypeapp.domain.usecase.seasontracker

import io.reactivex.Completable
import pl.hypeapp.domain.executor.PostExecutionThread
import pl.hypeapp.domain.executor.ThreadExecutor
import pl.hypeapp.domain.model.SeasonTrackerModel
import pl.hypeapp.domain.repository.SeasonTrackerRepository
import pl.hypeapp.domain.usecase.base.AbsRxCompletableUseCase
import javax.inject.Inject

class UpdateSeasonTrackerUseCase @Inject constructor(threadExecutor: ThreadExecutor,
                                                     postExecutionThread: PostExecutionThread,
                                                     private val repository: SeasonTrackerRepository)
    : AbsRxCompletableUseCase<UpdateSeasonTrackerUseCase.Params>(threadExecutor, postExecutionThread) {

    override fun createCompletable(params: Params): Completable {
        return repository.addSeasonToSeasonTracker(params.seasonTrackerModel)
    }

    class Params private constructor(val seasonTrackerModel: SeasonTrackerModel) {
        companion object {
            fun createQuery(seasonTrackerModel: SeasonTrackerModel): Params {
                return Params(seasonTrackerModel)
            }
        }
    }

}
