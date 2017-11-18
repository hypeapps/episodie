package pl.hypeapp.domain.usecase.premieres

import io.reactivex.Completable
import pl.hypeapp.domain.executor.PostExecutionThread
import pl.hypeapp.domain.executor.ThreadExecutor
import pl.hypeapp.domain.model.PremiereReminderModel
import pl.hypeapp.domain.repository.PremiereDatesRepository
import pl.hypeapp.domain.usecase.base.AbsRxCompletableUseCase
import javax.inject.Inject

class AddPremiereReminderUseCase @Inject constructor(threadExecutor: ThreadExecutor,
                                                     postExecutionThread: PostExecutionThread,
                                                     private val repository: PremiereDatesRepository)
    : AbsRxCompletableUseCase<AddPremiereReminderUseCase.Params>(threadExecutor, postExecutionThread) {

    override fun createCompletable(params: Params): Completable {
        return repository.addPremiereReminder(params.premiereReminderModel)
    }

    class Params private constructor(val premiereReminderModel: PremiereReminderModel) {
        companion object {
            fun createQuery(premiereReminderModel: PremiereReminderModel): Params {
                return Params(premiereReminderModel)
            }
        }
    }
}
