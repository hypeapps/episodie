package pl.hypeapp.domain.usecase.seasons

import io.reactivex.Single
import pl.hypeapp.domain.executor.PostExecutionThread
import pl.hypeapp.domain.executor.ThreadExecutor
import pl.hypeapp.domain.model.AllSeasonsModel
import pl.hypeapp.domain.repository.AllSeasonsRepository
import pl.hypeapp.domain.usecase.base.AbsRxSingleUseCase
import pl.hypeapp.domain.usecase.mapwatched.TvShowWatchStateMapper
import javax.inject.Inject

class AllEpisodesUseCase @Inject constructor(threadExecutor: ThreadExecutor,
                                             postExecutionThread: PostExecutionThread,
                                             private val watchStateMapper: TvShowWatchStateMapper,
                                             private val repository: AllSeasonsRepository)
    : AbsRxSingleUseCase<AllSeasonsModel, AllEpisodesUseCase.Params>(threadExecutor, postExecutionThread) {

    override fun createSingle(params: Params): Single<AllSeasonsModel> =
            repository.getAllSeasons(params.tvShowId, params.update)
                    .map {
                        watchStateMapper.map(it)
                        it
                    }

    class Params private constructor(val tvShowId: String, val update: Boolean) {
        companion object {
            fun createQuery(tvShowId: String, update: Boolean): Params {
                return Params(tvShowId, update)
            }
        }
    }
}
