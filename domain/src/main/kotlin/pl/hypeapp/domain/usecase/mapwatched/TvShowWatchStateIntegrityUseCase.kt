package pl.hypeapp.domain.usecase.mapwatched

import io.reactivex.Single
import pl.hypeapp.domain.executor.PostExecutionThread
import pl.hypeapp.domain.executor.ThreadExecutor
import pl.hypeapp.domain.model.TvShowModel
import pl.hypeapp.domain.usecase.base.AbsRxSingleUseCase
import javax.inject.Inject

class TvShowWatchStateIntegrityUseCase @Inject constructor(threadExecutor: ThreadExecutor,
                                                           postExecutionThread: PostExecutionThread,
                                                           private val mapWatchStateMapper: TvShowWatchStateMapper)
    : AbsRxSingleUseCase<List<TvShowModel>, TvShowWatchStateIntegrityUseCase.Params>(threadExecutor, postExecutionThread) {

    override fun createSingle(params: Params): Single<List<TvShowModel>> =
            Single.just(params.tvShows).map {
                mapWatchStateMapper.map(it)
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
