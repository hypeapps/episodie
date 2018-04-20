package pl.hypeapp.domain.usecase.watchstate

import io.reactivex.Completable
import pl.hypeapp.domain.executor.PostExecutionThread
import pl.hypeapp.domain.executor.ThreadExecutor
import pl.hypeapp.domain.model.WatchState
import pl.hypeapp.domain.model.tvshow.SeasonModel
import pl.hypeapp.domain.repository.WatchedShowRepository
import pl.hypeapp.domain.usecase.allepisodes.AllEpisodesUseCase
import pl.hypeapp.domain.usecase.base.AbsRxCompletableUseCase
import pl.hypeapp.domain.usecase.watchstate.mapwatched.MapTvShowWatchStateUseCase
import javax.inject.Inject

class UpdateSeasonWatchStateUseCase @Inject constructor(threadExecutor: ThreadExecutor,
                                                        postExecutionThread: PostExecutionThread,
                                                        private val allEpisodesUseCase: AllEpisodesUseCase,
                                                        private val mapTvShowWatchStateUseCase: MapTvShowWatchStateUseCase,
                                                        private val showRepository: WatchedShowRepository)
    : AbsRxCompletableUseCase<UpdateSeasonWatchStateUseCase.Params>(threadExecutor, postExecutionThread) {

    override fun createCompletable(params: Params): Completable {
        return allEpisodesUseCase.execute(AllEpisodesUseCase.Params.createQuery(params.seasonModel.tvShowId!!, false))
                .flatMap { mapTvShowWatchStateUseCase.execute(MapTvShowWatchStateUseCase.Params.createParams(it)) }
                .flatMapCompletable {
                    if (params.addToWatched) {
                        it.setSeasonWatchStateById(params.seasonModel.seasonId!!, WatchState.WATCHED)
                    } else {
                        it.setSeasonWatchStateById(params.seasonModel.seasonId!!, WatchState.NOT_WATCHED)
                    }
                    showRepository.addTvShowToWatched(it)
                }
    }

    class Params private constructor(val seasonModel: SeasonModel, val addToWatched: Boolean) {
        companion object {
            fun createParams(seasonModel: SeasonModel, addToWatched: Boolean): Params {
                return Params(seasonModel, addToWatched)
            }
        }
    }

}
