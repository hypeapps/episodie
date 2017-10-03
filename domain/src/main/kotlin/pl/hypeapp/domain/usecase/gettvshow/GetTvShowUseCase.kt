package pl.hypeapp.domain.usecase.gettvshow

import io.reactivex.Single
import pl.hypeapp.domain.executor.PostExecutionThread
import pl.hypeapp.domain.executor.ThreadExecutor
import pl.hypeapp.domain.model.TvShowModel
import pl.hypeapp.domain.repository.TvShowRepository
import pl.hypeapp.domain.usecase.base.AbsRxSingleUseCase
import javax.inject.Inject

class GetTvShowUseCase @Inject constructor(threadExecutor: ThreadExecutor,
                                           postExecutionThread: PostExecutionThread,
                                           private val repository: TvShowRepository)
    : AbsRxSingleUseCase<TvShowModel, GetTvShowUseCase.Params>(threadExecutor, postExecutionThread) {

    override fun createSingle(params: Params): Single<TvShowModel> {
        return repository.getTvShow(params.id, params.update)
    }

    class Params private constructor(val id: String, val update: Boolean) {
        companion object {
            fun createParams(id: String, update: Boolean): Params {
                return Params(id, update)
            }
        }
    }

}
