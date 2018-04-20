package pl.hypeapp.domain.usecase.search

import io.reactivex.Single
import pl.hypeapp.domain.executor.PostExecutionThread
import pl.hypeapp.domain.executor.ThreadExecutor
import pl.hypeapp.domain.model.tvshow.TvShowModel
import pl.hypeapp.domain.repository.SearchRepository
import pl.hypeapp.domain.usecase.base.AbsRxSingleUseCase
import pl.hypeapp.domain.usecase.watchstate.mapwatched.WatchStateMapper
import javax.inject.Inject

class BasicSearchUseCase @Inject constructor(threadExecutor: ThreadExecutor,
                                             postExecutionThread: PostExecutionThread,
                                             private val watchStateMapper: WatchStateMapper,
                                             private val repository: SearchRepository)
    : AbsRxSingleUseCase<List<TvShowModel>, BasicSearchUseCase.Params>(threadExecutor, postExecutionThread) {

    override fun createSingle(params: Params): Single<List<TvShowModel>> =
            repository.basicSearch(params.query)
                    .map {
                        it.forEach { watchStateMapper.map(it) }
                        it
                    }

    class Params private constructor(val query: String) {
        companion object {
            fun createQuery(query: String): Params {
                return Params(query)
            }
        }
    }
}
