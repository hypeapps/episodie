package pl.hypeapp.domain.usecase.top

import io.reactivex.Single
import pl.hypeapp.domain.executor.PostExecutionThread
import pl.hypeapp.domain.executor.ThreadExecutor
import pl.hypeapp.domain.model.PageableRequest
import pl.hypeapp.domain.model.TopListModel
import pl.hypeapp.domain.repository.TopListRepository
import pl.hypeapp.domain.usecase.base.AbsRxSingleUseCase
import pl.hypeapp.domain.usecase.mapwatched.TvShowWatchStateMapper
import javax.inject.Inject

class TopListUseCase @Inject constructor(threadExecutor: ThreadExecutor,
                                         postExecutionThread: PostExecutionThread,
                                         private val watchStateMapper: TvShowWatchStateMapper,
                                         private val repository: TopListRepository)
    : AbsRxSingleUseCase<TopListModel, TopListUseCase.Params>(threadExecutor, postExecutionThread) {

    override fun createSingle(params: Params): Single<TopListModel> =
            repository.getTopList(params.pageableRequest, params.update)
                    .map {
                        watchStateMapper.map(it.tvShows)
                        it
                    }

    class Params private constructor(val pageableRequest: PageableRequest, val update: Boolean) {
        companion object {
            fun createQuery(page: Int, size: Int, update: Boolean): Params {
                return Params(PageableRequest(page = page, size = size), update)
            }
        }
    }
}
