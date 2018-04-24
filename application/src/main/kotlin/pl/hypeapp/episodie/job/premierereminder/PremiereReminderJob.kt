package pl.hypeapp.episodie.job.premierereminder

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import com.evernote.android.job.Job
import com.evernote.android.job.JobManager
import com.evernote.android.job.JobRequest
import com.evernote.android.job.util.support.PersistableBundleCompat
import pl.hypeapp.episodie.R
import pl.hypeapp.episodie.ui.features.mainfeed.MainFeedActivity
import java.util.*

class PremiereReminderJob : Job() {

    override fun onRunJob(params: Params?): Result {
        showReminder(context, params?.extras?.getString(EXTRAS_TV_SHOW_TITLE, ""))
        return Result.SUCCESS
    }

    private fun showReminder(context: Context, title: String?) {
        val pendingIntent = PendingIntent.getActivity(context, 0,
                Intent(context, MainFeedActivity::class.java), 0)
        val notification = NotificationCompat.Builder(context, JOB_TAG)
                .setContentTitle(String.format(context.getString(R.string.format_new_premiere), title))
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLocalOnly(true)
                .build()
        NotificationManagerCompat.from(context).notify(Random().nextInt(), notification)
    }


    companion object {
        val JOB_TAG = "PREMIERE_REMINDER_JOB"
        val EXTRAS_TV_SHOW_TITLE = "EXTRA_TV_SHOW_NAME"

        fun cancelJob(id: Int) = JobManager.instance().cancel(id)

        fun scheduleJob(exactTime: Long, title: String): Int {
            val extras = PersistableBundleCompat()
            extras.apply {
                putString(EXTRAS_TV_SHOW_TITLE, title)
            }
            return JobRequest.Builder(JOB_TAG)
                    .setExact(exactTime)
                    .addExtras(extras)
                    .build()
                    .schedule()
        }
    }
}
