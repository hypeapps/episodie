package pl.hypeapp.domain.usecase.yourlibrary

import io.reactivex.Single
import pl.hypeapp.domain.executor.PostExecutionThread
import pl.hypeapp.domain.executor.ThreadExecutor
import pl.hypeapp.domain.model.Pageable
import pl.hypeapp.domain.model.watched.WatchedTvShowModel
import pl.hypeapp.domain.repository.WatchedShowRepository
import pl.hypeapp.domain.usecase.base.AbsRxSingleUseCase
import pl.hypeapp.domain.usecase.gettvshow.GetTvShowUseCase
import pl.hypeapp.domain.usecase.watchstate.mapwatched.WatchStateMapper
import javax.inject.Inject

class GetWatchedTvShowUseCase @Inject constructor(threadExecutor: ThreadExecutor,
                                                  postExecutionThread: PostExecutionThread,
                                                  private val showRepository: WatchedShowRepository,
                                                  private val getTvShowUseCase: GetTvShowUseCase,
                                                  private val watchStateMapper: WatchStateMapper)
    : AbsRxSingleUseCase<Pageable<WatchedTvShowModel>, GetWatchedTvShowUseCase.Params>(threadExecutor, postExecutionThread) {

    override fun createSingle(params: Params): Single<Pageable<WatchedTvShowModel>> {
        return showRepository.getWatchedTvShows(params.page, params.size)
                .map {
                    it.content.map {
                        it.tvShowModel = getTvShowUseCase.execute(GetTvShowUseCase.Params.createParams(it.tvShowId, params.update)).blockingGet()
                        it.tvShowModel?.let {
                            watchStateMapper.map(it)
                        }
                    }
                    it
                }
    }

    class Params private constructor(val page: Int, val size: Int, val update: Boolean) {
        companion object {
            fun createQuery(page: Int, size: Int, update: Boolean): Params {
                return Params(page, size, update)
            }
        }
    }

}
