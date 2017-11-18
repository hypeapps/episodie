package pl.hypeapp.episodie.job

import com.evernote.android.job.Job
import com.evernote.android.job.JobCreator
import pl.hypeapp.episodie.job.episodereminder.EpisodeReminderEngine
import pl.hypeapp.episodie.job.episodereminder.EpisodeReminderJob
import pl.hypeapp.episodie.job.premierereminder.PremiereReminderJob

class ReminderJobCreator constructor(private val episodeReminderEngine: EpisodeReminderEngine) : JobCreator {

    override fun create(tag: String): Job? {
        return when (tag) {
            EpisodeReminderJob.JOB_TAG -> EpisodeReminderJob(episodeReminderEngine)
            EpisodeReminderJob.SYNC_JOB_TAG -> EpisodeReminderJob(episodeReminderEngine)
            PremiereReminderJob.JOB_TAG -> PremiereReminderJob()
            else -> throw RuntimeException("Wrong job tag")
        }
    }

}
