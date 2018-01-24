package pl.hypeapp.episodie.navigation

import android.annotation.TargetApi
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.support.v4.app.ActivityOptionsCompat
import android.view.View
import pl.hypeapp.domain.model.TvShowModel
import pl.hypeapp.episodie.R
import pl.hypeapp.episodie.ui.features.mainfeed.MainFeedActivity
import pl.hypeapp.episodie.ui.features.search.SearchActivity
import pl.hypeapp.episodie.ui.features.seasontracker.SeasonTrackerActivity
import pl.hypeapp.episodie.ui.features.timecalculator.TimeCalculatorActivity
import pl.hypeapp.episodie.ui.features.tvshowdetails.TvShowDetailsActivity
import pl.hypeapp.episodie.ui.viewmodel.TvShowModelParcelable

object Navigator {

    fun startTvShowDetails(activity: Activity, tvShowModel: TvShowModel) {
        val intent = Intent(activity, TvShowDetailsActivity::class.java)
        intent.putExtra(EXTRA_INTENT_TV_SHOW_MODEL, TvShowModelParcelable(tvShowModel))
        activity.startActivity(intent)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun startTvShowDetailsWithSharedElement(activity: Activity, tvShowModel: TvShowModel, transitionViews: Array<out View>) {
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

    fun startFeedActivity(activity: Activity) {
        val intent = Intent(activity, MainFeedActivity::class.java)
        activity.startActivity(intent)
    }

    fun startSearchActivity(activity: Activity) {
        val intent = Intent(activity, SearchActivity::class.java)
        activity.startActivity(intent)
    }

    fun startTimeCalculatorAcitvity(activity: Activity) {
        val intent = Intent(activity, TimeCalculatorActivity::class.java)
        activity.startActivity(intent)
    }

    fun startSeasonTrackerActivity(activity: Activity) {
        val intent = Intent(activity, SeasonTrackerActivity::class.java)
        activity.startActivity(intent)
    }

}
