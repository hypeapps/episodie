package pl.hypeapp.episodie.navigation

import android.annotation.TargetApi
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.support.v4.app.ActivityOptionsCompat
import android.view.View
import pl.hypeapp.domain.model.tvshow.TvShowModel
import pl.hypeapp.episodie.R
import pl.hypeapp.episodie.ui.features.mainfeed.MainFeedActivity
import pl.hypeapp.episodie.ui.features.search.SearchActivity
import pl.hypeapp.episodie.ui.features.seasontracker.SeasonTrackerActivity
import pl.hypeapp.episodie.ui.features.timecalculator.TimeCalculatorActivity
import pl.hypeapp.episodie.ui.features.tvshowdetails.TvShowDetailsActivity
import pl.hypeapp.episodie.ui.features.yourlibrary.YourLibraryActivity
import pl.hypeapp.episodie.ui.viewmodel.TvShowModelParcelable

object Navigator {

    fun startTvShowDetails(activity: Activity, tvShowModel: TvShowModel) {
        val intent = Intent(activity, TvShowDetailsActivity::class.java)
        intent.putExtra(EXTRA_INTENT_TV_SHOW_MODEL, TvShowModelParcelable(tvShowModel))
        activity.startActivityForResult(intent, 1)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun startTvShowDetailsWithSharedElements(activity: Activity, tvShowModel: TvShowModel, transitionViews: Array<out View>) {
        val intent = Intent(activity, TvShowDetailsActivity::class.java)
        intent.putExtra(EXTRA_INTENT_TV_SHOW_MODEL, TvShowModelParcelable(tvShowModel))
        val p1 = android.support.v4.util.Pair.create(transitionViews[0], activity.getString(R.string.cover_transition))
        val p2 = android.support.v4.util.Pair.create(transitionViews[1], activity.getString(R.string.title_transition))
        val p3 = android.support.v4.util.Pair.create(transitionViews[2], "no_transition")
        val activityOptions: ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, p1, p2, p3)
        activity.startActivity(intent, activityOptions.toBundle())
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun startTvShowDetailsWithSharedElement(activity: Activity, tvShowModel: TvShowModel, transitionView: View) {
        val intent = Intent(activity, TvShowDetailsActivity::class.java)
        intent.putExtra(EXTRA_INTENT_TV_SHOW_MODEL, TvShowModelParcelable(tvShowModel))
        val p1 = android.support.v4.util.Pair.create(transitionView, activity.getString(R.string.cover_transition))
        val activityOptions: ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, p1)
        activity.startActivity(intent, activityOptions.toBundle())
    }

    fun startFeedActivity(callingActivity: Activity) {
        val intent = Intent(callingActivity, MainFeedActivity::class.java)
        callingActivity.startActivity(intent)
        if (callingActivity !is MainFeedActivity) callingActivity.finish()
    }

    fun startSearchActivity(callingActivity: Activity) {
        val intent = Intent(callingActivity, SearchActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_NEW_TASK)
        callingActivity.startActivity(intent)
        if (callingActivity !is MainFeedActivity) callingActivity.finish()
    }

    fun startTimeCalculatorActivity(callingActivity: Activity) {
        val intent = Intent(callingActivity, TimeCalculatorActivity::class.java)
        callingActivity.startActivity(intent)
        if (callingActivity !is MainFeedActivity) callingActivity.finish()
    }

    fun startYourLibraryActivity(callingActivity: Activity) {
        val intent = Intent(callingActivity, YourLibraryActivity::class.java)
        callingActivity.startActivity(intent)
        if (callingActivity !is MainFeedActivity) callingActivity.finish()
    }

    fun startSeasonTrackerActivity(callingActivity: Activity) {
        val intent = Intent(callingActivity, SeasonTrackerActivity::class.java)
        callingActivity.startActivity(intent)
        if (callingActivity !is MainFeedActivity) callingActivity.finish()
    }

}
