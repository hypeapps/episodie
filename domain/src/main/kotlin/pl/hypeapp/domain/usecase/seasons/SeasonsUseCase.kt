package pl.hypeapp.domain.usecase.seasons

import io.reactivex.Single
import pl.hypeapp.domain.executor.PostExecutionThread
import pl.hypeapp.domain.executor.ThreadExecutor
import pl.hypeapp.domain.model.AllSeasonsModel
import pl.hypeapp.domain.repository.AllSeasonsRepository
import pl.hypeapp.domain.usecase.base.AbsRxSingleUseCase
import javax.inject.Inject

class SeasonsUseCase @Inject constructor(threadExecutor: ThreadExecutor,
                                         postExecutionThread: PostExecutionThread,
                                         private val repository: AllSeasonsRepository)
    : AbsRxSingleUseCase<AllSeasonsModel, SeasonsUseCase.Params>(threadExecutor, postExecutionThread) {

    override fun createSingle(params: Params): Single<AllSeasonsModel> =
            repository.getAllSeasons(params.tvShowId, params.update)

    class Params private constructor(val tvShowId: String, val update: Boolean) {
        companion object {
            fun createQuery(tvShowId: String, update: Boolean): Params {
                return Params(tvShowId, update)
            }
        }
    }
}
