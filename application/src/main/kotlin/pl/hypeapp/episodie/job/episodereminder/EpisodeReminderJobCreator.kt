package pl.hypeapp.episodie.job.episodereminder

import com.evernote.android.job.Job
import com.evernote.android.job.JobCreator

class EpisodeReminderJobCreator constructor(private val episodeReminderEngine: EpisodeReminderEngine) : JobCreator {

    override fun create(tag: String): Job? {
        return when (tag) {
            EpisodeReminderJob.JOB_TAG -> EpisodeReminderJob(episodeReminderEngine)
            EpisodeReminderJob.SYNC_JOB_TAG -> EpisodeReminderJob(episodeReminderEngine)
            else -> throw RuntimeException("Wrong job tag")
        }
    }

}
