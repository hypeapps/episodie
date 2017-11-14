package pl.hypeapp.domain.usecase.watchstate

import io.reactivex.Completable
import pl.hypeapp.domain.executor.PostExecutionThread
import pl.hypeapp.domain.executor.ThreadExecutor
import pl.hypeapp.domain.model.EpisodeModel
import pl.hypeapp.domain.model.TvShowExtendedModel
import pl.hypeapp.domain.repository.WatchedRepository
import pl.hypeapp.domain.usecase.allepisodes.AllEpisodesUseCase
import pl.hypeapp.domain.usecase.base.AbsRxCompletableUseCase
import javax.inject.Inject

class ManageTvShowWatchStateUseCase @Inject constructor(threadExecutor: ThreadExecutor,
                                                        postExecutionThread: PostExecutionThread,
                                                        private val allEpisodesUseCase: AllEpisodesUseCase,
                                                        private val repository: WatchedRepository)
    : AbsRxCompletableUseCase<ManageTvShowWatchStateUseCase.Params>(threadExecutor, postExecutionThread) {

    override fun createCompletable(params: ManageTvShowWatchStateUseCase.Params): Completable {
        return allEpisodesUseCase
                .execute(AllEpisodesUseCase.Params.createQuery(params.tvShowId, update = false))
                .flatMapCompletable {
                    if (params.addToWatched) {
                        repository.addTvShowToWatched(ManageTvShowWatchStateUseCase.EpisodesParams
                                .createParams(it)
                                .episodeModels)
                    } else {
                        repository.deleteWatchedTvShow(params.tvShowId)
                    }
                }
    }

    class Params private constructor(val tvShowId: String, val addToWatched: Boolean) {
        companion object {
            fun createParams(tvShowId: String, addToWatched: Boolean): Params {
                return Params(tvShowId, addToWatched)
            }
        }
    }

    private class EpisodesParams private constructor(val episodeModels: ArrayList<EpisodeModel>) {
        companion object {
            fun createParams(tvShowExtendedModel: TvShowExtendedModel): EpisodesParams {
                val episodesModels: ArrayList<EpisodeModel> = arrayListOf()
                tvShowExtendedModel.seasons?.forEach {
                    it.episodes?.map {
                        episodesModels.add(it)
                    }
                }
                return EpisodesParams(episodesModels)
            }
        }
    }

}
