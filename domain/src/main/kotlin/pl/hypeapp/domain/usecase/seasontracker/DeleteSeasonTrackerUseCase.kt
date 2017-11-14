package pl.hypeapp.domain.usecase.seasontracker

import io.reactivex.Completable
import pl.hypeapp.domain.executor.PostExecutionThread
import pl.hypeapp.domain.executor.ThreadExecutor
import pl.hypeapp.domain.repository.SeasonTrackerRepository
import pl.hypeapp.domain.usecase.base.AbsRxCompletableUseCase
import javax.inject.Inject


class DeleteSeasonTrackerUseCase @Inject constructor(threadExecutor: ThreadExecutor,
                                                     postExecutionThread: PostExecutionThread,
                                                     private val repository: SeasonTrackerRepository)
    : AbsRxCompletableUseCase<Void?>(threadExecutor, postExecutionThread) {


    override fun createCompletable(params: Void?): Completable = repository.deleteSeasonTracker()

}
