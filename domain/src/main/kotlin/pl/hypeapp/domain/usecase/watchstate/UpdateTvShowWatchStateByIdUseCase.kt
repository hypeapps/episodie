package pl.hypeapp.domain.usecase.watchstate

import io.reactivex.Completable
import pl.hypeapp.domain.executor.PostExecutionThread
import pl.hypeapp.domain.executor.ThreadExecutor
import pl.hypeapp.domain.usecase.allepisodes.AllEpisodesUseCase
import pl.hypeapp.domain.usecase.base.AbsRxCompletableUseCase
import javax.inject.Inject

class UpdateTvShowWatchStateByIdUseCase @Inject constructor(threadExecutor: ThreadExecutor,
                                                            postExecutionThread: PostExecutionThread,
                                                            private val allEpisodesUseCase: AllEpisodesUseCase,
                                                            private val updateTvShowWatchStateUseCase: UpdateTvShowWatchStateUseCase)
    : AbsRxCompletableUseCase<UpdateTvShowWatchStateByIdUseCase.Params>(threadExecutor, postExecutionThread) {

    override fun createCompletable(params: UpdateTvShowWatchStateByIdUseCase.Params): Completable {
        return allEpisodesUseCase
                .execute(AllEpisodesUseCase.Params.createQuery(params.tvShowId, update = false))
                .flatMapCompletable {
                    updateTvShowWatchStateUseCase.execute(UpdateTvShowWatchStateUseCase.Params.createParams(it, params.addToWatched))
                }
    }

    class Params private constructor(val tvShowId: String, val addToWatched: Boolean) {
        companion object {
            fun createParams(tvShowId: String, addToWatched: Boolean): Params {
                return Params(tvShowId, addToWatched)
            }
        }
    }

}
