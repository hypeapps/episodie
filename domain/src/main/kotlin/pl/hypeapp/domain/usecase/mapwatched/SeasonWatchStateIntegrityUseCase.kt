package pl.hypeapp.domain.usecase.mapwatched

import io.reactivex.Single
import pl.hypeapp.domain.executor.PostExecutionThread
import pl.hypeapp.domain.executor.ThreadExecutor
import pl.hypeapp.domain.model.TvShowExtendedModel
import pl.hypeapp.domain.usecase.base.AbsRxSingleUseCase
import javax.inject.Inject

class SeasonWatchStateIntegrityUseCase @Inject constructor(threadExecutor: ThreadExecutor,
                                                           postExecutionThread: PostExecutionThread,
                                                           private val mapWatchStateMapper: TvShowWatchStateMapper)
    : AbsRxSingleUseCase<TvShowExtendedModel, SeasonWatchStateIntegrityUseCase.Params>(threadExecutor, postExecutionThread) {

    override fun createSingle(params: Params): Single<TvShowExtendedModel> =
            Single.just(params.tvShowExtendedModel).map {
                mapWatchStateMapper.map(it)
                it
            }

    class Params private constructor(val tvShowExtendedModel: TvShowExtendedModel) {
        companion object {
            fun createParams(allSeasonModel: TvShowExtendedModel): Params {
                return Params(allSeasonModel)
            }
        }
    }

}
