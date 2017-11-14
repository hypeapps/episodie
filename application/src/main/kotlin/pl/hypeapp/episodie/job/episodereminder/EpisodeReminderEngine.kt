package pl.hypeapp.episodie.job.episodereminder

import com.evernote.android.job.JobManager
import pl.hypeapp.domain.model.EpisodeReminderModel
import pl.hypeapp.domain.usecase.base.DefaultCompletableObserver
import pl.hypeapp.domain.usecase.base.DefaultSingleObserver
import pl.hypeapp.domain.usecase.episodereminder.GetEpisodeRemindersUseCase
import pl.hypeapp.domain.usecase.episodereminder.SyncEpisodeRemindersUseCase
import pl.hypeapp.episodie.ui.viewmodel.seasontracker.SeasonTrackerViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EpisodeReminderEngine @Inject constructor(private val syncEpisodeRemindersUseCase: SyncEpisodeRemindersUseCase,
                                                private val getEpisodeRemindersUseCase: GetEpisodeRemindersUseCase) {

    fun scheduleReminders(seasonTrackerModel: SeasonTrackerViewModel) = with(seasonTrackerModel) {
        syncEpisodeRemindersUseCase.execute(SynEpisodeReminderObserver(),
                SyncEpisodeRemindersUseCase.Params.createParams(seasonTrackerModel.seasonTrackerModel?.tvShowId,
                        seasonTrackerModel.seasonTrackerModel?.seasonId))
    }

    fun syncReminder(tvShowId: String, seasonId: String) {
        syncEpisodeRemindersUseCase.execute(SynEpisodeReminderObserver(),
                SyncEpisodeRemindersUseCase.Params.createParams(tvShowId, seasonId))
        EpisodeReminderJob.scheduleDailySync(tvShowId, seasonId)
    }

    fun scheduleNextPossibleReminder() = getEpisodeRemindersUseCase.execute(GetEpisodeRemindersObserver(), null)

    fun cancelDailySync() = JobManager.instance().cancelAllForTag(EpisodeReminderJob.SYNC_JOB_TAG)

    fun cancelReminder() = JobManager.instance().cancelAllForTag(EpisodeReminderJob.JOB_TAG)

    inner class SynEpisodeReminderObserver : DefaultCompletableObserver() {
        override fun onComplete() = scheduleNextPossibleReminder()
    }

    inner class GetEpisodeRemindersObserver : DefaultSingleObserver<List<EpisodeReminderModel>>() {
        override fun onSuccess(model: List<EpisodeReminderModel>) {
            if (model.isNotEmpty()) {
                // We need model of the latest premiere and schedule it
                val reminderModel: EpisodeReminderModel? =
                        model.minWith(Comparator { p0, p1 -> if (p0.timestamp > p1.timestamp) 1 else 0 })
                reminderModel?.let {
                    EpisodeReminderJob.scheduleJob(it)
                }
            }
        }
    }

}
