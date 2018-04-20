package pl.hypeapp.episodie.ui.features.timecalculator

import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.transition.TransitionManager
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import butterknife.OnClick
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.jakewharton.rxbinding2.widget.RxTextView
import com.miguelcatalan.materialsearchview.MaterialSearchView
import kotlinx.android.synthetic.main.activity_time_calculator.*
import pl.hypeapp.domain.model.tvshow.TvShowModel
import pl.hypeapp.episodie.App
import pl.hypeapp.episodie.R
import pl.hypeapp.episodie.di.components.ActivityComponent
import pl.hypeapp.episodie.di.components.DaggerActivityComponent
import pl.hypeapp.episodie.di.module.ActivityModule
import pl.hypeapp.episodie.extensions.getStatusBarHeight
import pl.hypeapp.episodie.extensions.viewVisible
import pl.hypeapp.episodie.glide.GlideApp
import pl.hypeapp.episodie.glide.transformation.BlurTransformation
import pl.hypeapp.episodie.navigation.Navigator
import pl.hypeapp.episodie.ui.base.BaseActivity
import pl.hypeapp.episodie.ui.features.navigationdrawer.NavigationDrawer
import pl.hypeapp.episodie.ui.features.timecalculator.adapter.TimeCalculatorDelegateAdapter
import pl.hypeapp.episodie.ui.features.timecalculator.adapter.TimeCalculatorRecyclerAdapter
import pl.hypeapp.episodie.ui.viewmodel.TvShowViewModel
import pl.hypeapp.presentation.timecalculator.TimeCalculatorPresenter
import pl.hypeapp.presentation.timecalculator.TimeCalculatorView
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TimeCalculatorActivity : BaseActivity(), TimeCalculatorView, MaterialSearchView.OnQueryTextListener,
        TimeCalculatorDelegateAdapter.OnSelectedListener {

    override fun getLayoutRes(): Int = R.layout.activity_time_calculator

    @Inject
    lateinit var presenter: TimeCalculatorPresenter

    @Inject
    lateinit var navigationDrawer: NavigationDrawer

    private lateinit var timeCalculatorRecyclerAdapter: TimeCalculatorRecyclerAdapter

    private var suggestionDialog: AlertDialog? = null

    private var episodeOrderSum = 0

    private var selectedSum = 0

    private var runtime = 0L

    private var transitionView: View? = null

    private var tipDialog: AlertDialog? = null

    private val component: ActivityComponent
        get() = DaggerActivityComponent.builder()
                .appComponent((application as App).component)
                .activityModule(ActivityModule(this))
                .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        component.inject(this)
        presenter.onAttachView(this)
        GlideApp.with(this)
                .load(R.drawable.breaking_bad_backgorund)
                .transform(MultiTransformation<Bitmap>(BlurTransformation(this, 17), CenterCrop()))
                .into(image_view_time_calculator_background)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetachView()
        disposeSubscription()
        dismissDialog(suggestionDialog)
        dismissDialog(tipDialog)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_time_calculator, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_time_calculator_tip) {
            tipDialog = createTipDialog()
            tipDialog?.show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun startEnterAnimation() {
        TransitionManager.beginDelayedTransition(constraint_layout_time_calculator)
        val guideLineParams: ConstraintLayout.LayoutParams = guideline_vertical_time_calculator.layoutParams as ConstraintLayout.LayoutParams
        guideLineParams.guidePercent = 0.30f
        guideline_vertical_time_calculator.layoutParams = guideLineParams
        YoYo.with(Techniques.TakingOff).playOn(text_view_time_calculator_info_header)
        startZoomInAnimation(time_calculator_runtime_view)
        startZoomInAnimation(image_view_time_calculator_icon_episodes)
        startZoomInAnimation(image_view_time_calculator_icon_selected)
    }

    override fun initSearchView() = with(time_calculator_search_view) {
        setOnQueryTextListener(this@TimeCalculatorActivity)
        findViewById<View>(R.id.transparent_view).background = null
        setSubmitOnClick(true)
        setSuggestionIcon(ContextCompat.getDrawable(this@TimeCalculatorActivity, R.drawable.all_ic_diamond_checked_dark))
        disposable = RxTextView.textChanges(time_calculator_search_view.findViewById(R.id.searchTextView))
                .filter { it.length > 2 }
                .debounce(TIMEOUT_DEBOUNCE, TimeUnit.MILLISECONDS)
                .map { it.toString() }
                .distinctUntilChanged()
                .filter { !it.isEmpty() }
                .subscribe { presenter.executeQuery(it) }
    }

    override fun initAdapter() {
        timeCalculatorRecyclerAdapter = TimeCalculatorRecyclerAdapter(TimeCalculatorDelegateAdapter(this))
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        recycler_view_time_calculator.apply {
            enableViewScaling(true)
            adapter = timeCalculatorRecyclerAdapter
            itemTouchHelper.attachToRecyclerView(this)
        }
    }

    override fun initNavigationDrawer() {
        toolbar_activity_all.apply {
            setSupportActionBar(this)
            supportActionBar?.setHomeButtonEnabled(true)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
            setPadding(paddingLeft, resources.getStatusBarHeight(), paddingRight, paddingBottom)
            navigationDrawer.initWithToolbar(this)
        }
        lifecycle.addObserver(navigationDrawer)
    }

    override fun setRecyclerItem(item: TvShowModel) {
        timeCalculatorRecyclerAdapter.addItem(TvShowViewModel(item))
        recycler_view_time_calculator.scrollToLastChild()
    }

    override fun setRecyclerItemWithDelay(item: TvShowModel, delay: Long) {
        recycler_view_time_calculator.postDelayed({
            setRecyclerItem(item)
        }, delay)
        recycler_view_time_calculator.scrollToLastChild()
    }

    override fun deleteRecyclerItemAt(adapterPosition: Int) {
        presenter.onItemRemove(timeCalculatorRecyclerAdapter.getItemAt(adapterPosition).tvShow)
        timeCalculatorRecyclerAdapter.deleteItem(adapterPosition)
        recycler_view_time_calculator.scrollToCenterView()
    }

    override fun addEpisodeOrder(episodeOrder: Int) {
        episodeOrderSum += episodeOrder
        startEpisodeOrderAnimation(String.format(getString(R.string.time_calculator_addition_episodes), episodeOrder), DELAY_START_ANIMATION)
    }

    override fun subtractEpisodeOrder(episodeOrder: Int) {
        episodeOrderSum -= episodeOrder
        startEpisodeOrderAnimation(String.format(getString(R.string.time_calculator_subtraction_episodes), episodeOrder), 0)
    }

    override fun incrementSelected() {
        selectedSum++
        startSelectedAnimation(getString(R.string.time_calculator_incerement_selected), DELAY_START_ANIMATION)
    }

    override fun decrementSelected() {
        selectedSum--
        startSelectedAnimation(getString(R.string.time_calculator_decrement_selected), 0)
    }

    override fun addRuntimeWithAnimation(episodeRuntime: Long) {
        this.runtime += episodeRuntime
        time_calculator_runtime_view.startIncrementRuntimeAnimation(this.runtime, episodeRuntime)
    }

    override fun subtractRuntimeWithAnimation(episodeRuntime: Long) {
        this.runtime -= episodeRuntime
        time_calculator_runtime_view.startDecrementRuntimeAnimation(this.runtime, episodeRuntime)
    }

    override fun dismissSearchView() = with(time_calculator_search_view) {
        if (isSearchOpen) {
            hideKeyboard(this@TimeCalculatorActivity.currentFocus)
            dismissSuggestions()
            time_calculator_search_view.setSuggestions(arrayOf(""))
            findViewById<EditText>(R.id.searchTextView).text.clear()
        }
    }

    @OnClick(R.id.text_view_time_calculator_backdrop)
    fun showSearchView() = time_calculator_search_view.showSearch()

    override fun setSearchSuggestions(suggestions: Array<String>) = time_calculator_search_view.setSuggestions(suggestions)

    override fun onQueryTextSubmit(query: String?): Boolean {
        presenter.onSearchQuerySubmit(query)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    override fun oItemSelected(basicSearchResultModel: TvShowModel, transitionView: View) {
        presenter.onItemSelected(basicSearchResultModel.id)
        this.transitionView = transitionView
    }

    override fun showSuggestionsDialog(suggestions: Array<String?>) {
        suggestionDialog = createSuggestionsDialog(suggestions).create()
        suggestionDialog?.show()
    }

    override fun showErrorToast() = Toast
            .makeText(this, getString(R.string.time_calculator_search_result_not_found), Toast.LENGTH_SHORT)
            .show()

    override fun showTvShowAlreadyAddedToast() = Toast
            .makeText(this, getString(R.string.time_calculator_tv_show_present), Toast.LENGTH_SHORT)
            .show()

    override fun openTvShowDetailsActivity(model: TvShowModel) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            transitionView?.let { Navigator.startTvShowDetailsWithSharedElement(this, model, it) }
        } else {
            Navigator.startTvShowDetails(this, model)
        }
    }

    private val simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.UP or ItemTouchHelper.DOWN) {
        override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
            return true
        }

        override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder?): Float {
            return SWIPE_THRESHOLD
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
            presenter.onSwiped(viewHolder?.adapterPosition)
        }
    }

    private fun startEpisodeOrderAnimation(episodeOrder: String, delay: Long) {
        text_view_time_calculator_episodes_increment.postDelayed({
            text_view_time_calculator_episodes_increment.viewVisible()
            evaporate_text_view_time_calculator_episodes.animateText((episodeOrderSum).toString())
            text_view_time_calculator_episodes_increment.text = episodeOrder
            YoYo.with(Techniques.TakingOff).playOn(text_view_time_calculator_episodes_increment)
        }, delay)
    }

    private fun startSelectedAnimation(operation: String, delay: Long) {
        text_view_time_calculator_selected_increment.postDelayed({
            text_view_time_calculator_selected_increment.viewVisible()
            evaporate_text_view_time_calculator_selected.animateText((selectedSum).toString())
            YoYo.with(Techniques.TakingOff).onStart {
                text_view_time_calculator_selected_increment.text = operation
            }.playOn(text_view_time_calculator_selected_increment)
        }, delay)
    }

    private fun createSuggestionsDialog(suggestions: Array<String?>): AlertDialog.Builder {
        val title = TextView(this).apply {
            text = getString(R.string.all_suggestion_dialog_title)
            textSize = 20f
            typeface = Typeface.createFromAsset(assets, getString(R.string.all_font_path))
            gravity = Gravity.CENTER
            setTextColor(ContextCompat.getColor(this@TimeCalculatorActivity, R.color.primary_dark))
        }
        return AlertDialog.Builder(TimeCalculatorActivity@ this)
                .setCustomTitle(title)
                .setItems(suggestions) { _, index -> presenter.onSearchQuerySubmit(suggestions[index]) }
    }

    private fun startZoomInAnimation(view: View) = YoYo.with(Techniques.ZoomIn)
            .onStart { view.viewVisible() }
            .playOn(view)

    private fun dismissDialog(dialog: Dialog?) = with(dialog) {
        this?.let {
            if (isShowing) {
                dismiss()
            }
        }
    }

    private fun createTipDialog(): AlertDialog = AlertDialog.Builder(this)
            .setIcon(R.drawable.all_ic_info)
            .setTitle(R.string.alert_dialog_tip_title)
            .setMessage(R.string.alert_dialog_season_tracker_tip_message)
            .setPositiveButton(R.string.alert_dialog_button_text) { p0, _ -> p0?.dismiss() }
            .create()

    private companion object {
        const val SWIPE_THRESHOLD = 0.1f
        const val TIMEOUT_DEBOUNCE = 200L
        const val DELAY_START_ANIMATION = 900L
    }

}
