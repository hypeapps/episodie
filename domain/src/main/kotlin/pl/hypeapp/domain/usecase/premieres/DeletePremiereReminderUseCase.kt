package pl.hypeapp.domain.usecase.premieres

import io.reactivex.Single
import pl.hypeapp.domain.executor.PostExecutionThread
import pl.hypeapp.domain.executor.ThreadExecutor
import pl.hypeapp.domain.model.PremiereReminderModel
import pl.hypeapp.domain.repository.PremiereDatesRepository
import pl.hypeapp.domain.usecase.base.AbsRxSingleUseCase
import javax.inject.Inject

class DeletePremiereReminderUseCase @Inject constructor(val threadExecutor: ThreadExecutor,
                                                        val postExecutionThread: PostExecutionThread,
                                                        private val getPremiereReminderUseCase: GetPremiereReminderUseCase,
                                                        private val repository: PremiereDatesRepository)
    : AbsRxSingleUseCase<PremiereReminderModel, DeletePremiereReminderUseCase.Params>(threadExecutor, postExecutionThread) {

    override fun createSingle(params: Params): Single<PremiereReminderModel> {
        return getPremiereReminderUseCase.execute(GetPremiereReminderUseCase.Params.createParams(params.tvShowId))
                .map {
                    repository.deletePremiereReminder(params.tvShowId)
                    it
                }
    }

    class Params private constructor(val tvShowId: String) {
        companion object {
            fun createQuery(tvShowId: String): Params {
                return Params(tvShowId)
            }
        }
    }
}
