package pl.hypeapp.domain.usecase.watchstate

import io.reactivex.Completable
import pl.hypeapp.domain.executor.PostExecutionThread
import pl.hypeapp.domain.executor.ThreadExecutor
import pl.hypeapp.domain.model.EpisodeModel
import pl.hypeapp.domain.repository.WatchedRepository
import pl.hypeapp.domain.usecase.base.AbsRxCompletableUseCase
import javax.inject.Inject

class ManageEpisodeWatchStateUseCase @Inject constructor(threadExecutor: ThreadExecutor,
                                                         postExecutionThread: PostExecutionThread,
                                                         private val repository: WatchedRepository)
    : AbsRxCompletableUseCase<ManageEpisodeWatchStateUseCase.Params>(threadExecutor, postExecutionThread) {

    override fun createCompletable(params: Params): Completable {
        return if (params.addToWatched) {
            repository.addEpisodeToWatched(params.episodeModel)
        } else {
            repository.deleteWatchedEpisode(params.episodeModel.episodeId!!)
        }
    }

    class Params private constructor(val episodeModel: EpisodeModel, val addToWatched: Boolean) {
        companion object {
            fun createParams(episodeModel: EpisodeModel, addToWatched: Boolean): Params {
                return Params(episodeModel, addToWatched)
            }
        }
    }

}
