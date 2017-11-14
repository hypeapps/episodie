package pl.hypeapp.domain.usecase.allepisodes

import io.reactivex.Single
import pl.hypeapp.domain.executor.PostExecutionThread
import pl.hypeapp.domain.executor.ThreadExecutor
import pl.hypeapp.domain.model.TvShowExtendedModel
import pl.hypeapp.domain.repository.AllSeasonsRepository
import pl.hypeapp.domain.usecase.base.AbsRxSingleUseCase
import pl.hypeapp.domain.usecase.mapwatched.TvShowWatchStateMapper
import javax.inject.Inject

class AllEpisodesUseCase @Inject constructor(threadExecutor: ThreadExecutor,
                                             postExecutionThread: PostExecutionThread,
                                             private val watchStateMapper: TvShowWatchStateMapper,
                                             private val repository: AllSeasonsRepository)
    : AbsRxSingleUseCase<TvShowExtendedModel, AllEpisodesUseCase.Params>(threadExecutor, postExecutionThread) {

    override fun createSingle(params: Params): Single<TvShowExtendedModel> {
        return if (params.afterPremiereDate) {
            repository.getAllSeasonsAfterPremiereDate(params.tvShowId, params.update)
                    .map {
                        watchStateMapper.map(it)
                        it
                    }
        } else {
            repository.getAllSeason(params.tvShowId, params.update)
        }
    }

    class Params private constructor(val tvShowId: String,
                                     val update: Boolean,
                                     val afterPremiereDate: Boolean) {
        companion object {
            fun createQuery(tvShowId: String, update: Boolean, afterPremiereDate: Boolean = true): Params {
                return Params(tvShowId, update, afterPremiereDate)
            }
        }
    }
}
