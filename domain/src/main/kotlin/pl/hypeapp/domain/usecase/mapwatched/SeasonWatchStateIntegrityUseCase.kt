package pl.hypeapp.domain.usecase.mapwatched

import io.reactivex.Single
import pl.hypeapp.domain.executor.PostExecutionThread
import pl.hypeapp.domain.executor.ThreadExecutor
import pl.hypeapp.domain.model.AllSeasonsModel
import pl.hypeapp.domain.usecase.base.AbsRxSingleUseCase
import javax.inject.Inject

class SeasonWatchStateIntegrityUseCase @Inject constructor(threadExecutor: ThreadExecutor,
                                                           postExecutionThread: PostExecutionThread,
                                                           private val mapWatchStateMapper: TvShowWatchStateMapper)
    : AbsRxSingleUseCase<AllSeasonsModel, SeasonWatchStateIntegrityUseCase.Params>(threadExecutor, postExecutionThread) {

    override fun createSingle(params: Params): Single<AllSeasonsModel> =
            Single.just(params.allSeasonsModel).map {
                mapWatchStateMapper.map(it)
                it
            }

    class Params private constructor(val allSeasonsModel: AllSeasonsModel) {
        companion object {
            fun createParams(allSeasonModel: AllSeasonsModel): Params {
                return Params(allSeasonModel)
            }
        }
    }

}
