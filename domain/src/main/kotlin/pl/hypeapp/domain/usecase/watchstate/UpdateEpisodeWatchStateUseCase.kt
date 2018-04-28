package pl.hypeapp.domain.usecase.watchstate

import io.reactivex.Completable
import pl.hypeapp.domain.executor.PostExecutionThread
import pl.hypeapp.domain.executor.ThreadExecutor
import pl.hypeapp.domain.model.WatchState
import pl.hypeapp.domain.model.tvshow.EpisodeModel
import pl.hypeapp.domain.repository.WatchedShowRepository
import pl.hypeapp.domain.usecase.base.AbsRxCompletableUseCase
import pl.hypeapp.domain.usecase.gettvshow.GetTvShowExtendedUseCase
import pl.hypeapp.domain.usecase.watchstate.mapwatched.MapTvShowWatchStateUseCase
import javax.inject.Inject

class UpdateEpisodeWatchStateUseCase @Inject constructor(threadExecutor: ThreadExecutor,
                                                         postExecutionThread: PostExecutionThread,
                                                         private val getTvShowExtendedUseCase: GetTvShowExtendedUseCase,
                                                         private val mapTvShowWatchStateUseCase: MapTvShowWatchStateUseCase,
                                                         private val showRepository: WatchedShowRepository)
    : AbsRxCompletableUseCase<UpdateEpisodeWatchStateUseCase.Params>(threadExecutor, postExecutionThread) {

    override fun createCompletable(params: Params): Completable {
        return getTvShowExtendedUseCase.execute(GetTvShowExtendedUseCase.Params.createQuery(params.episodeModel.tvShowId!!, false))
                .flatMap { mapTvShowWatchStateUseCase.execute(MapTvShowWatchStateUseCase.Params.createParams(it)) }
                .flatMapCompletable {
                    with(params) {
                        if (addToWatched) {
                            it.setEpisodeWatchStateById(episodeModel.episodeId!!, episodeModel.seasonId!!, WatchState.WATCHED)
                        } else {
                            it.setEpisodeWatchStateById(episodeModel.episodeId!!, episodeModel.seasonId!!, WatchState.NOT_WATCHED)
                        }
                    }
                    showRepository.addTvShowToWatched(it)
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
