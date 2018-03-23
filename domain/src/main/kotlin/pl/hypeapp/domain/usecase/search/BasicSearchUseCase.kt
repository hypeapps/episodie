package pl.hypeapp.domain.usecase.search

import io.reactivex.Single
import pl.hypeapp.domain.executor.PostExecutionThread
import pl.hypeapp.domain.executor.ThreadExecutor
import pl.hypeapp.domain.model.search.BasicSearchResultModel
import pl.hypeapp.domain.repository.SearchRepository
import pl.hypeapp.domain.usecase.base.AbsRxSingleUseCase
import javax.inject.Inject

class BasicSearchUseCase @Inject constructor(threadExecutor: ThreadExecutor,
                                             postExecutionThread: PostExecutionThread,
                                             private val repository: SearchRepository)
    : AbsRxSingleUseCase<List<BasicSearchResultModel>, BasicSearchUseCase.Params>(threadExecutor, postExecutionThread) {

    override fun createSingle(params: Params): Single<List<BasicSearchResultModel>> =
            repository.basicSearch(params.query)

    class Params private constructor(val query: String) {
        companion object {
            fun createQuery(query: String): Params {
                return Params(query)
            }
        }
    }
}
