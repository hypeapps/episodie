package pl.hypeapp.domain.usecase.watchstate

import io.reactivex.Completable
import pl.hypeapp.domain.executor.PostExecutionThread
import pl.hypeapp.domain.executor.ThreadExecutor
import pl.hypeapp.domain.model.WatchState
import pl.hypeapp.domain.model.tvshow.TvShowExtendedModel
import pl.hypeapp.domain.repository.WatchedShowRepository
import pl.hypeapp.domain.usecase.base.AbsRxCompletableUseCase
import javax.inject.Inject

class UpdateTvShowWatchStateUseCase @Inject constructor(threadExecutor: ThreadExecutor,
                                                        postExecutionThread: PostExecutionThread,
                                                        private val watchedShowRepository: WatchedShowRepository)
    : AbsRxCompletableUseCase<UpdateTvShowWatchStateUseCase.Params>(threadExecutor, postExecutionThread) {

    override fun createCompletable(params: Params): Completable {
        return if (params.addToWatched) {
            params.tvShowModel.watchState = WatchState.WATCHED
            watchedShowRepository.addTvShowToWatched(params.tvShowModel)
        } else {
            watchedShowRepository.deleteWatchedTvShow(params.tvShowModel)
        }
    }

    class Params private constructor(val tvShowModel: TvShowExtendedModel, val addToWatched: Boolean) {
        companion object {
            fun createParams(tvShowModel: TvShowExtendedModel, addToWatched: Boolean): Params {
                return Params(tvShowModel, addToWatched)
            }
        }
    }
}
