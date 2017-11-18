package pl.hypeapp.domain.usecase.premieres

import io.reactivex.Single
import pl.hypeapp.domain.executor.PostExecutionThread
import pl.hypeapp.domain.executor.ThreadExecutor
import pl.hypeapp.domain.model.PageableRequest
import pl.hypeapp.domain.model.PremiereDatesModel
import pl.hypeapp.domain.repository.PremiereDatesRepository
import pl.hypeapp.domain.usecase.base.AbsRxSingleUseCase
import java.util.*
import javax.inject.Inject

class PremiereDatesUseCase @Inject constructor(threadExecutor: ThreadExecutor,
                                               postExecutionThread: PostExecutionThread,
                                               private val repository: PremiereDatesRepository)
    : AbsRxSingleUseCase<PremiereDatesModel, PremiereDatesUseCase.Params>(threadExecutor, postExecutionThread) {

    override fun createSingle(params: Params): Single<PremiereDatesModel> {
        return repository.getPremiereDates(params.pageableRequest, params.fromDate, params.update)
                .map {
                    it.premiereDates?.filter { it.id?.equals(repository.getPremiereReminderById(it.id).tvShowId)!! }
                            ?.map { it.notificationScheduled = true }
                    it
                }
    }

    class Params private constructor(val pageableRequest: PageableRequest,
                                     val fromDate: Date,
                                     val update: Boolean) {
        companion object {
            fun createQuery(page: Int, size: Int, update: Boolean): Params {
                return Params(PageableRequest(page = page, size = size), getTwoWeeksAgoDate(), update)
            }

            private fun getTwoWeeksAgoDate(): Date {
                val twoWeeksAgo = Calendar.getInstance()
                twoWeeksAgo.add(Calendar.DATE, -14)
                return twoWeeksAgo.time
            }
        }
    }
}
