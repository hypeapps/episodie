package pl.hypeapp.domain.usecase.premieres

import io.reactivex.Single
import pl.hypeapp.domain.executor.PostExecutionThread
import pl.hypeapp.domain.executor.ThreadExecutor
import pl.hypeapp.domain.model.PremiereReminderModel
import pl.hypeapp.domain.repository.PremiereDatesRepository
import pl.hypeapp.domain.usecase.base.AbsRxSingleUseCase
import javax.inject.Inject

class GetPremiereReminderUseCase @Inject constructor(threadExecutor: ThreadExecutor,
                                                     postExecutionThread: PostExecutionThread,
                                                     private val repository: PremiereDatesRepository)
    : AbsRxSingleUseCase<PremiereReminderModel, GetPremiereReminderUseCase.Params>(threadExecutor, postExecutionThread) {

    override fun createSingle(params: Params): Single<PremiereReminderModel> {
        return repository.getPremiereReminderSingleById(params.tvShowId)
    }

    class Params constructor(val tvShowId: String) {
        companion object {
            fun createParams(tvShowId: String): Params {
                return Params(tvShowId)
            }
        }
    }

}
