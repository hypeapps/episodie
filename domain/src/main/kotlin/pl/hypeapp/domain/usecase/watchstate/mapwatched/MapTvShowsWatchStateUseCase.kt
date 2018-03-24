package pl.hypeapp.domain.usecase.watchstate.mapwatched

import io.reactivex.Single
import pl.hypeapp.domain.executor.PostExecutionThread
import pl.hypeapp.domain.executor.ThreadExecutor
import pl.hypeapp.domain.model.tvshow.TvShowModel
import pl.hypeapp.domain.usecase.base.AbsRxSingleUseCase
import javax.inject.Inject

class MapTvShowsWatchStateUseCase @Inject constructor(threadExecutor: ThreadExecutor,
                                                      postExecutionThread: PostExecutionThread,
                                                      private val watchStateMapper: WatchStateMapper)
    : AbsRxSingleUseCase<List<TvShowModel>, MapTvShowsWatchStateUseCase.Params>(threadExecutor, postExecutionThread) {

    override fun createSingle(params: Params): Single<List<TvShowModel>> =
            Single.just(params.tvShows).map {
                it.forEach { watchStateMapper.map(it) }
                it
            }

    class Params private constructor(val tvShows: List<TvShowModel>) {
        companion object {
            fun createParams(tvShows: List<TvShowModel>): Params {
                return Params(tvShows)
            }
        }
    }

}
