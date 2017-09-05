package pl.hypeapp.domain.usecase.watchstate

import io.reactivex.Completable
import pl.hypeapp.domain.executor.PostExecutionThread
import pl.hypeapp.domain.executor.ThreadExecutor
import pl.hypeapp.domain.model.EpisodeModel
import pl.hypeapp.domain.model.SeasonModel
import pl.hypeapp.domain.repository.WatchedRepository
import pl.hypeapp.domain.usecase.base.AbsRxCompletableUseCase
import javax.inject.Inject

class ManageSeasonsWatchStateUseCase @Inject constructor(threadExecutor: ThreadExecutor,
                                                         postExecutionThread: PostExecutionThread,
                                                         private val repository: WatchedRepository)
    : AbsRxCompletableUseCase<ManageSeasonsWatchStateUseCase.Params>(threadExecutor, postExecutionThread) {

    override fun createCompletable(params: Params): Completable {
        return if (params.addToWatched) {
            repository.addSeasonToWatched(params.episodeModels)
        } else {
            repository.deleteWatchedSeason(params.seasonId)
        }
    }

    class Params private constructor(val episodeModels: ArrayList<EpisodeModel>,
                                     val seasonId: String,
                                     val addToWatched: Boolean) {
        companion object {
            fun createParams(seasonModel: SeasonModel, addToWatched: Boolean): Params {
                val episodesModels: ArrayList<EpisodeModel> = arrayListOf()
                seasonModel.episodes?.forEach {
                    episodesModels.add(it)
                }
                return Params(episodesModels, seasonModel.seasonId!!, addToWatched)
            }
        }
    }

}
