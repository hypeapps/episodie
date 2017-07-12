package pl.hypeapp.domain.usecase.mostpopular

import io.reactivex.Single
import pl.hypeapp.domain.executor.PostExecutionThread
import pl.hypeapp.domain.executor.ThreadExecutor
import pl.hypeapp.domain.model.MostPopularModel
import pl.hypeapp.domain.model.PageableRequest
import pl.hypeapp.domain.repository.MostPopularRepository
import pl.hypeapp.domain.usecase.base.AbsRxSingleUseCase
import javax.inject.Inject

class MostPopularUseCase @Inject constructor(threadExecutor: ThreadExecutor,
                                             postExecutionThread: PostExecutionThread,
                                             private val repository: MostPopularRepository)
    : AbsRxSingleUseCase<MostPopularModel, MostPopularUseCase.Params>(threadExecutor, postExecutionThread) {

    override fun createSingle(params: Params): Single<MostPopularModel> =
            repository.getMostPopular(params.pageableRequest, params.update)

    class Params private constructor(val pageableRequest: PageableRequest,
                                     val update: Boolean) {
        companion object {
            fun createQuery(page: Int, size: Int, update: Boolean): Params {
                return Params(PageableRequest(page = page, size = size), update)
            }
        }
    }
}
