package pl.hypeapp.episodie.job.episodereminder

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import com.evernote.android.job.Job
import com.evernote.android.job.JobRequest
import com.evernote.android.job.util.support.PersistableBundleCompat
import pl.hypeapp.domain.model.EpisodeReminderModel
import pl.hypeapp.episodie.R
import pl.hypeapp.episodie.ui.features.seasontracker.SeasonTrackerActivity
import java.util.*
import java.util.concurrent.TimeUnit

class EpisodeReminderJob constructor(private val episodeReminderEngine: EpisodeReminderEngine) : Job() {

    override fun onRunJob(params: Params?): Result = params?.extras.let {
        if (it?.getBoolean(EXTRA_SYNC_JOB, false)!!) {
            episodeReminderEngine.syncReminder(it.getString(EXTRA_TV_SHOW_ID, null),
                    it.getString(EXTRA_SEASON_ID, null))
        } else {
            params?.let {
                showReminder(context, params)
            }
            episodeReminderEngine.scheduleNextPossibleReminder()
        }
        return Result.SUCCESS
    }

    private fun showReminder(context: Context, params: Params) = with(params.extras) {
        val pendingIntent = PendingIntent.getActivity(context, 0,
                Intent(context, SeasonTrackerActivity::class.java), 0)
        val notification = NotificationCompat.Builder(context, EpisodeReminderJob.JOB_TAG)
                .setContentTitle(getString(EXTRA_TITLE, " ") + context.getString(R.string.notification_new_episode_title))
                .setContentInfo("#${getInt(EXTRA_EPISODE_NUMBER, 0)} ${getString(EXTRA_EPISODE_NAME, "")} ")
                .setContentText("#${getInt(EXTRA_EPISODE_NUMBER, 0)} ${getString(EXTRA_EPISODE_NAME, "")} ")
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLocalOnly(true)
                .build()
        NotificationManagerCompat.from(context).notify(Random().nextInt(), notification)
    }

    companion object {
        val JOB_TAG = "EPISODE_REMINDER_JOB"
        val SYNC_JOB_TAG: String = "SYNC_JOB"
        val EXTRA_ID = "EXTRA_ID"
        val EXTRA_TITLE = "EXTRA_TITLE"
        val EXTRA_EPISODE_NAME = "EXTRA_EPISODE_NAME"
        val EXTRA_SYNC_JOB = "EXTRA_SYNC_JOB"
        val EXTRA_EPISODE_NUMBER = "EXTRA_EPISODE_NUMBER"
        val EXTRA_TV_SHOW_ID = "EXTRA_TV_SHOW_ID"
        val EXTRA_SEASON_ID = "EXTRA_SEASON_ID"

        fun scheduleJob(episodeReminderModel: EpisodeReminderModel): Int {
            val extras = PersistableBundleCompat()
            extras.apply {
                putInt(EXTRA_ID, episodeReminderModel.jobId)
                putString(EXTRA_TITLE, episodeReminderModel.tvShowName + ":")
                putString(EXTRA_EPISODE_NAME, episodeReminderModel.name)
                putInt(EXTRA_EPISODE_NUMBER, episodeReminderModel.episodeNumber)
                putString(EXTRA_TV_SHOW_ID, episodeReminderModel.tvShowId)
                putString(EXTRA_SEASON_ID, episodeReminderModel.seasonId)
            }
            return JobRequest.Builder(JOB_TAG)
                    .setExact(episodeReminderModel.timestamp)
                    .setExtras(extras)
                    .setUpdateCurrent(true)
                    .build()
                    .schedule()
        }

        fun scheduleDailySync(tvShowId: String, seasonId: String): Int {
            val extras = PersistableBundleCompat()
            extras.apply {
                putString(EXTRA_TV_SHOW_ID, tvShowId)
                putString(EXTRA_SEASON_ID, seasonId)
                putBoolean(EXTRA_SYNC_JOB, true)
            }
            return JobRequest.Builder(SYNC_JOB_TAG)
                    .setExtras(extras)
                    .setRequiredNetworkType(JobRequest.NetworkType.METERED)
                    .setPeriodic(TimeUnit.HOURS.toMillis(6), TimeUnit.HOURS.toMillis(3))
                    .setUpdateCurrent(true)
                    .build()
                    .schedule()
        }

    }
}
