package pl.hypeapp.episodie.ui.features.seasontracker

import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.Typeface
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import butterknife.OnClick
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.facebook.device.yearclass.YearClass
import com.jakewharton.rxbinding2.widget.RxTextView
import com.miguelcatalan.materialsearchview.MaterialSearchView
import kotlinx.android.synthetic.main.activity_season_tracker.*
import kotlinx.android.synthetic.main.layout_season_tracker_search.*
import pl.hypeapp.domain.model.collections.SeasonTrackerModel
import pl.hypeapp.domain.model.tvshow.SeasonModel
import pl.hypeapp.domain.model.tvshow.TvShowExtendedModel
import pl.hypeapp.episodie.App
import pl.hypeapp.episodie.Prefs
import pl.hypeapp.episodie.R
import pl.hypeapp.episodie.di.components.ActivityComponent
import pl.hypeapp.episodie.di.components.DaggerActivityComponent
import pl.hypeapp.episodie.di.module.ActivityModule
import pl.hypeapp.episodie.extensions.*
import pl.hypeapp.episodie.glide.GlideApp
import pl.hypeapp.episodie.glide.transformation.BlurTransformation
import pl.hypeapp.episodie.job.episodereminder.EpisodeReminderEngine
import pl.hypeapp.episodie.ui.base.BaseViewModelActivity
import pl.hypeapp.episodie.ui.features.navigationdrawer.NavigationDrawer
import pl.hypeapp.episodie.ui.features.seasontracker.adapter.OnEpisodeWatchedListener
import pl.hypeapp.episodie.ui.features.seasontracker.adapter.SeasonTrackerRecyclerAdapter
import pl.hypeapp.episodie.ui.features.seasontracker.dialog.OnDialogItemClickListener
import pl.hypeapp.episodie.ui.features.seasontracker.dialog.SelectSeasonDialog
import pl.hypeapp.episodie.ui.viewmodel.seasontracker.SeasonTrackerViewModel
import pl.hypeapp.presentation.seasontracker.SeasonTrackerPresenter
import pl.hypeapp.presentation.seasontracker.SeasonTrackerView
import javax.inject.Inject

class SeasonTrackerActivity : BaseViewModelActivity<SeasonTrackerViewModel>(), SeasonTrackerView,
        MaterialSearchView.OnQueryTextListener, OnDialogItemClickListener, OnEpisodeWatchedListener {

    override fun getLayoutRes(): Int = R.layout.activity_season_tracker

    override val viewModelClass: Class<SeasonTrackerViewModel>
        get() = SeasonTrackerViewModel::class.java

    @Inject lateinit var presenter: SeasonTrackerPresenter

    @Inject lateinit var prefs: Prefs

    @Inject lateinit var episodeReminderEngine: EpisodeReminderEngine

    private lateinit var seasonTrackerRecyclerAdapter: SeasonTrackerRecyclerAdapter

    @Inject
    lateinit var navigationDrawer: NavigationDrawer

    private var suggestionDialog: AlertDialog? = null

    private var selectSeasonDialog: SelectSeasonDialog? = null

    private val component: ActivityComponent
        get() = DaggerActivityComponent.builder()
                .appComponent((application as App).component)
                .activityModule(ActivityModule(this))
                .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setNavigationBarOptions()
        component.inject(this)
        presenter.onAttachView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetachView()
        disposeSubscription()
        dismissDialog(suggestionDialog)
        dismissDialog(selectSeasonDialog)
    }

    override fun getModel(): SeasonTrackerModel? = viewModel.seasonTrackerModel

    override fun retainViewModel(seasonTrackerModel: SeasonTrackerModel) = viewModel.retainModel(seasonTrackerModel)

    override fun clearModel() = viewModel.clearModel()

    override fun retainSeasonViewModel(season: SeasonModel, tvShowExtendedModel: TvShowExtendedModel) =
            viewModel.retainSeasonModel(season, tvShowExtendedModel)

    override fun initSearchView() = with(binge_watching_search_view) {
        setOnQueryTextListener(this@SeasonTrackerActivity)
        findViewById<View>(R.id.transparent_view).background = null
        setSubmitOnClick(true)
        setSuggestionIcon(ContextCompat.getDrawable(this@SeasonTrackerActivity, R.drawable.all_ic_diamond_checked_dark))
        disposable = RxTextView.textChanges(binge_watching_search_view.findViewById(R.id.searchTextView))
                .filter { it.length > 2 }
                .debounce(TIMEOUT_DEBOUNCE, java.util.concurrent.TimeUnit.MILLISECONDS)
                .map { it.toString() }
                .distinctUntilChanged()
                .filter { !it.isEmpty() }
                .subscribe { presenter.executeQuery(it) }
    }

    override fun initNavigationDrawer() {
        toolbar_activity_all.apply {
            setSupportActionBar(this)
            supportActionBar?.setHomeButtonEnabled(true)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
            setPadding(paddingLeft, resources.getStatusBarHeight(), paddingRight, paddingBottom)
        }
        lifecycle.addObserver(navigationDrawer)
    }

    override fun loadTvShowHeader() {
        stub_binge_watching_header?.let {
            if (YearClass.CLASS_2013 < YearClass.get(applicationContext)) {
                stub_binge_watching_header.layoutResource = R.layout.noise_view_background_tv_show_details
            } else {
                stub_binge_watching_header.layoutResource = R.layout.image_view_background_tv_show_details
            }
        }
        stub_binge_watching_header?.viewVisible()
        findViewById<View>(R.id.background_tv_show_details)?.viewVisible()
        GlideApp.with(this)
                .load(viewModel.imageOriginal)
                .override(340, 560)
                .thumbnail(GlideApp.with(this).load(viewModel.imageMedium).transform(BlurTransformation(this, 20)))
                .transform(MultiTransformation<Bitmap>(BlurTransformation(this, 20), CenterCrop()))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(findViewById(R.id.background_tv_show_details))
    }

    override fun initSeasonTracker() {
        seasonTrackerRecyclerAdapter = SeasonTrackerRecyclerAdapter(this)
        recycler_view_season_tracker.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(false)
            adapter = seasonTrackerRecyclerAdapter
            viewVisible()
        }
        seasonTrackerRecyclerAdapter.addHeader(viewModel.headerViewModel!!)
        seasonTrackerRecyclerAdapter.addEpisodes(viewModel.episodes!!)
        fab_button_menu_season_tracker.viewVisible()
    }

    override fun updateRecyclerView() = seasonTrackerRecyclerAdapter.updateItems(viewModel.headerViewModel!!, viewModel.episodes!!)

    override fun showSearchComponent() {
        view_stub_binge_watching_search_component?.viewVisible()
        season_tracker_search_component.viewVisible()
        YoYo.with(Techniques.SlideInUp).duration(300).playOn(season_tracker_search_component)
        fab_button_menu_season_tracker.collapseImmediately()
        fab_button_menu_season_tracker.viewGone()
        recycler_view_season_tracker.viewGone()
        findViewById<View>(R.id.background_tv_show_details)?.viewGone()
        enableSearchView()
        GlideApp.with(this)
                .load(R.drawable.stranger2_background)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transform(MultiTransformation<Bitmap>(BlurTransformation(this, 2), CenterCrop()))
                .into(image_view_binge_watching_background)
    }

    override fun hideSearch(): Unit = with(season_tracker_search_component) {
        this?.let {
            if (it.visibility == View.VISIBLE) {
                YoYo.with(Techniques.TakingOff).onEnd { season_tracker_search_component.viewGone() }
                        .playOn(it)
            }
        }
    }

    override fun dismissSearchView() = with(binge_watching_search_view) {
        if (isSearchOpen) {
            hideKeyboard(this@SeasonTrackerActivity.currentFocus)
            dismissSuggestions()
            binge_watching_search_view.setSuggestions(arrayOf(""))
            findViewById<EditText>(R.id.searchTextView).text.clear()
        }
    }

    override fun setSearchSuggestions(suggestions: Array<String>) = binge_watching_search_view.setSuggestions(suggestions)

    private fun createSuggestionsDialog(suggestions: Array<String?>): AlertDialog.Builder {
        val title = TextView(this).apply {
            text = getString(R.string.all_suggestion_dialog_title)
            textSize = 20f
            typeface = Typeface.createFromAsset(assets, getString(R.string.all_font_path))
            gravity = Gravity.CENTER
            setTextColor(ContextCompat.getColor(this@SeasonTrackerActivity, R.color.primary_dark))
        }
        return AlertDialog.Builder(TimeCalculatorActivity@ this)
                .setCustomTitle(title)
                .setItems(suggestions) { _, index -> presenter.onSearchQuerySubmit(suggestions[index]) }
    }

    override fun showSuggestionsDialog(suggestions: Array<String?>) {
        suggestionDialog = createSuggestionsDialog(suggestions).create()
        suggestionDialog?.show()
    }

    override fun showErrorToast() = Toast
            .makeText(this, getString(R.string.time_calculator_search_result_not_found), Toast.LENGTH_SHORT)
            .show()

    override fun showErrorView() = view_error_season_tracker.viewVisible()

    override fun hideErrorView() = view_error_season_tracker.viewGone()

    @OnClick(R.id.button_item_error_retry)
    override fun onRetry() = presenter.onRetry()

    override fun onQueryTextSubmit(query: String?): Boolean {
        presenter.onSearchQuerySubmit(query)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    override fun onDialogItemSelected(season: SeasonModel) {
        selectSeasonDialog?.hide()
        presenter.onSeasonSelected(season)
    }

    override fun showSelectSeasonDialog(tvShowExtendedModel: TvShowExtendedModel) {
        selectSeasonDialog = SelectSeasonDialog(this, tvShowExtendedModel, this)
        selectSeasonDialog?.show()
    }

    override fun onEpisodeSelect(episodeId: String, view: View) {
        viewModel.addEpisode(episodeId)
        smallBangAnimator.bang(view)
        presenter.updateSeasonTracker(viewModel.seasonTrackerModel)
        if (viewModel.seasonTrackerModel?.watchedEpisodes?.size == viewModel.episodes?.size) {
            AlertDialog.Builder(this)
                    .setCancelable(true)
                    .setMessage(String.format(getString(R.string.dialog_on_season_completed_message),
                            viewModel.title,
                            viewModel.headerViewModel?.seasonModel?.seasonNumber,
                            getFullRuntimeFormatted(resources, viewModel.headerViewModel?.watchedRuntime),
                            viewModel.headerViewModel?.watchedEpisodes))
                    .setPositiveButton(getString(R.string.dialog_season_tracker_start_new), { dialogInterface, _ ->
                        dialogInterface.dismiss()
                        presenter.onRestartSeasonTracker()
                    })
                    .create()
                    .show()
        }
    }

    override fun onEpisodeDeselect(episodeId: String, view: View) {
        viewModel.removeEpisode(episodeId)
        smallBangAnimator.bang(view)
        presenter.updateSeasonTracker(viewModel.seasonTrackerModel)
    }

    override fun showLoading() = view_loading.viewVisible()

    override fun hideLoading() = view_loading.viewGone()

    override fun scheduleNotifications() {
        if (!prefs.isNotificationsSeasonTrackedDisplayed) {
            Snackbar.make(constraint_layout_season_tracker, getString(R.string.snackbar_message_about_notifications),
                    Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.snackbar_button_disable), { cancelNotifications() })
                    .setActionTextColor(ContextCompat.getColor(this, android.R.color.white))
                    .show()
            prefs.isNotificationsSeasonTrackedDisplayed = true
        }
        if (!prefs.isEpisodeReminderNotificationsStarted and !prefs.isNotificationsCanceled) {
            prefs.reminderSeasonId = viewModel.seasonTrackerModel?.seasonId ?: ""
            prefs.reminderTvShowId = viewModel.seasonTrackerModel?.tvShowId ?: ""
            episodeReminderEngine.scheduleReminders(viewModel)
            episodeReminderEngine.syncReminder(prefs.reminderTvShowId, prefs.reminderTvShowId)
            prefs.isEpisodeReminderNotificationsStarted = true
        }
        if (prefs.isEpisodeReminderNotificationsStarted) {
            fab_button_disable_notifications.apply {
                setIconDrawable(ContextCompat.getDrawable(this@SeasonTrackerActivity, R.drawable.all_ic_notifications_off)!!)
                title = getString(R.string.fab_button_notifications_off)
            }
        }
    }

    override fun cancelNotifications() {
        episodeReminderEngine.cancelDailySync()
        episodeReminderEngine.cancelReminder()
        prefs.isEpisodeReminderNotificationsStarted = false
        prefs.isNotificationsCanceled = true
        fab_button_disable_notifications.apply {
            setIconDrawable(ContextCompat.getDrawable(this@SeasonTrackerActivity, R.drawable.all_ic_notifications_on)!!)
            title = getString(R.string.fab_button_notifications_on)
        }
    }

    @OnClick(R.id.fab_button_start_new)
    fun onRestartSeasonTracker() {
        viewModel.clearModel()
        presenter.onRestartSeasonTracker()
        prefs.clearPrefs()
    }

    @OnClick(R.id.fab_button_disable_notifications)
    fun onDisableNotifications() {
        if (prefs.isEpisodeReminderNotificationsStarted) {
            cancelNotifications()
        } else {
            prefs.isNotificationsCanceled = false
            scheduleNotifications()
        }
    }

    @OnClick(R.id.view_hamburger_season_tracker)
    fun onHamburgerClick() = navigationDrawer.toggleDrawer()

    private fun enableSearchView() = text_view_binge_watching_backdrop.setOnClickListener {
        binge_watching_search_view.showSearch()
    }

    private fun dismissDialog(dialog: Dialog?) = with(dialog) {
        this?.let {
            if (isShowing) {
                dismiss()
            }
        }
    }

    private fun setNavigationBarOptions() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        if (isLandscapeOrientation() and !isNavigationBarLandscape()) {
            setLandscapeNavBarPaddingEnd(constraint_layout_season_tracker)
        }
    }

    private fun setLandscapeNavBarPaddingEnd(vararg views: View) = views.forEach {
        with(it) {
            setPadding(paddingStart, paddingTop, (paddingEnd + getNavigationBarSize().x), paddingBottom)
        }
    }

    private companion object {
        val TIMEOUT_DEBOUNCE = 200L
    }

}
