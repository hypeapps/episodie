package pl.hypeapp.domain.usecase.episodereminder

import io.reactivex.Single
import pl.hypeapp.domain.executor.PostExecutionThread
import pl.hypeapp.domain.executor.ThreadExecutor
import pl.hypeapp.domain.model.reminder.EpisodeReminderModel
import pl.hypeapp.domain.repository.EpisodeReminderRepository
import pl.hypeapp.domain.usecase.base.AbsRxSingleUseCase
import javax.inject.Inject

class GetEpisodeRemindersUseCase @Inject constructor(threadExecutor: ThreadExecutor,
                                                     postExecutionThread: PostExecutionThread,
                                                     private val repository: EpisodeReminderRepository)
    : AbsRxSingleUseCase<List<EpisodeReminderModel>, Void?>(threadExecutor, postExecutionThread) {

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun createSingle(unused: Void?): Single<List<EpisodeReminderModel>> {
        return repository.getReminders()
    }

}
