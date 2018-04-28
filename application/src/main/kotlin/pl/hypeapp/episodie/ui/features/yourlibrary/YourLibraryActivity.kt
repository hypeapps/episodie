package pl.hypeapp.episodie.ui.features.yourlibrary

import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.LinearInterpolator
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.flaviofaria.kenburnsview.RandomTransitionGenerator
import com.jakewharton.rxbinding2.view.RxView
import com.yarolegovich.discretescrollview.DiscreteScrollView
import com.yarolegovich.discretescrollview.transform.Pivot
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import kotlinx.android.synthetic.main.activity_your_library.*
import kotlinx.android.synthetic.main.item_error.button_item_error_retry
import kotlinx.android.synthetic.main.item_error.parent_item_error
import kotlinx.android.synthetic.main.layout_your_library_empty.button_layout_empty_message_feed
import kotlinx.android.synthetic.main.layout_your_library_empty.button_layout_empty_message_search
import kotlinx.android.synthetic.main.layout_your_library_empty.parent_view_empty_message
import pl.hypeapp.domain.model.tvshow.TvShowModel
import pl.hypeapp.domain.model.watched.UserStatsModel
import pl.hypeapp.domain.model.watched.WatchedTvShowModel
import pl.hypeapp.episodie.App
import pl.hypeapp.episodie.R
import pl.hypeapp.episodie.di.components.ActivityComponent
import pl.hypeapp.episodie.di.components.DaggerActivityComponent
import pl.hypeapp.episodie.di.module.ActivityModule
import pl.hypeapp.episodie.extensions.*
import pl.hypeapp.episodie.glide.GlideApp
import pl.hypeapp.episodie.glide.isValidContextForGlide
import pl.hypeapp.episodie.glide.transformation.BlurTransformation
import pl.hypeapp.episodie.glide.transformation.GrayscaleTransformation
import pl.hypeapp.episodie.navigation.Navigator
import pl.hypeapp.episodie.navigation.STATE_CHANGED
import pl.hypeapp.episodie.ui.base.BaseViewModelActivity
import pl.hypeapp.episodie.ui.base.adapter.ViewTypeDelegateAdapter
import pl.hypeapp.episodie.ui.features.navigationdrawer.NavigationDrawer
import pl.hypeapp.episodie.ui.features.yourlibrary.adapter.YourLibraryDelegateAdapter
import pl.hypeapp.episodie.ui.features.yourlibrary.adapter.YourLibraryRecyclerAdapter
import pl.hypeapp.episodie.ui.viewmodel.WatchedTvShowViewModel
import pl.hypeapp.episodie.ui.viewmodel.YourLibraryViewModel
import pl.hypeapp.presentation.yourlibrary.YourLibraryPresenter
import pl.hypeapp.presentation.yourlibrary.YourLibraryView
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class YourLibraryActivity : BaseViewModelActivity<YourLibraryViewModel>(), YourLibraryView,
        ViewTypeDelegateAdapter.OnViewSelectedListener, ViewTypeDelegateAdapter.OnRetryListener,
        DiscreteScrollView.OnItemChangedListener<RecyclerView.ViewHolder> {

    override val viewModelClass: Class<YourLibraryViewModel> = YourLibraryViewModel::class.java

    override fun getLayoutRes(): Int = R.layout.activity_your_library

    override var isLastPage: Boolean
        get() = viewModel.isLastPage
        set(value) {
            viewModel.isLastPage = value
        }

    var yourLibraryRecyclerAdapter: YourLibraryRecyclerAdapter = YourLibraryRecyclerAdapter(
            YourLibraryDelegateAdapter(this), this)

    @Inject
    lateinit var presenter: YourLibraryPresenter

    @Inject
    lateinit var navigationDrawer: NavigationDrawer

    val errorSnackBar: Snackbar by lazy {
        Snackbar.make(parent_layout_your_library, getString(R.string.item_error_message), Snackbar.LENGTH_INDEFINITE)
                .setActionTextColor(Color.WHITE)
                .setAction(getString(R.string.item_error_retry), { onRetry() })
    }

    private var isFirstCall = true

    private var tipDialog: AlertDialog? = null

    private val component: ActivityComponent
        get() = DaggerActivityComponent.builder()
                .appComponent((application as App).component)
                .activityModule(ActivityModule(this))
                .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        setNavigationBarOptions()
        presenter.onAttachView(this)
        if (viewModel.isError) showError()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_your_library, menu)
        menu?.getItem(0)?.isVisible = !viewModel.watchedShows.isEmpty()
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_your_library_tip) {
            tipDialog = createTipDialog()
            tipDialog?.show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        presenter.onDetachView()
        watched_show_your_library_scroll_view.removeItemChangedListener(this)
        watched_show_your_library_scroll_view.adapter = null
        dismissDialog(tipDialog)
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == STATE_CHANGED) {
            presenter.onStateChanged()
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

    override fun initRecyclerAdapter() {
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        watched_show_your_library_scroll_view.apply {
            adapter = yourLibraryRecyclerAdapter
            setItemTransformer(ScaleTransformer.Builder()
                    .setMaxScale(1.0f)
                    .setMinScale(0.8f)
                    .setPivotX(Pivot.X.CENTER)
                    .setPivotY(Pivot.Y.BOTTOM)
                    .build())
            isNestedScrollingEnabled = true
            setSlideOnFling(true)
            addOnItemChangedListener(this@YourLibraryActivity)
            itemTouchHelper.attachToRecyclerView(this)
        }
    }

    override fun loadImageBackground() {
        image_view_your_library_background.loadDrawableResource(R.drawable.your_library_image_background)
    }

    override fun setPrimaryColorBackground() {
        image_view_your_library_background.setImageDrawable(null)
        image_view_your_library_background.setBackgroundColor(ContextCompat.getColor(this, R.color.primary))
    }

    override fun loadViewModel() = viewModel.loadModel(
            populateRecyclerList = {
                yourLibraryRecyclerAdapter.addItems(viewModel.watchedShows)
                view_watched_show_your_library.viewVisible()
            },
            requestModel = {
                presenter.requestModel(page = viewModel.page, update = false)
            })

    override fun setWatchedShows(model: List<WatchedTvShowModel>) {
        viewModel.retainModel(model)
        yourLibraryRecyclerAdapter.addItems(viewModel.watchedShows)
        if (view_watched_show_your_library.visibility == View.GONE) view_watched_show_your_library.viewVisible()
        // Temp workaround because onCurrentItemChanged is not always called, especially for the first time in some cases.
        if (isFirstCall) {
            Glide.with(this)
                    .asBitmap()
                    .load(viewModel.watchedShows[watched_show_your_library_scroll_view.currentItem].tvShow?.imageMedium)
                    .into(applyWatchedShowDetails(viewModel.watchedShows[watched_show_your_library_scroll_view.currentItem]))
            initKenBurnsView(viewModel.watchedShows[watched_show_your_library_scroll_view.currentItem].tvShow?.imageMedium)
            isFirstCall = false
        }
        invalidateOptionsMenu()
    }

    // We need fresh state so we won't call recreate()
    override fun recreateLibrary() {
        val intent = intent
        finish()
        startActivity(intent)
    }

    override fun deleteRecyclerItemAt(adapterPosition: Int) {
        viewModel.deleteItem(yourLibraryRecyclerAdapter.getItemAt(adapterPosition))
        if (viewModel.watchedShows.isEmpty()) {
            showEmptyLibraryMessage()
        }
        presenter.onItemRemoved(yourLibraryRecyclerAdapter.getItemAt(adapterPosition).tvShow)
        yourLibraryRecyclerAdapter.deleteItem(adapterPosition)
    }

    override fun setUserStats(model: UserStatsModel) = with(model) {
        text_view_your_library_watched_episodes.text = watchedEpisodesCount.toString()
        text_view_your_library_watched_seasons.text = watchedSeasonsCount.toString()
        text_view_your_library_watched_shows.text = watchedTvShowsCount.toString()
        text_view_your_library_watching_time.text = getFullRuntimeFormatted(resources, watchingTime)
    }

    override fun hideUserStatsView() {
        text_view_your_library_watched_episodes.viewInvisible()
        text_view_your_library_watched_seasons.viewInvisible()
        text_view_your_library_watched_shows.viewInvisible()
        text_view_your_library_watching_time.viewInvisible()
    }

    override fun showRuntimeNotification(oldUserRuntime: Long, newRuntime: Long) {
        alerter_your_library.show(oldUserRuntime, newRuntime)
    }

    override fun showError() {
        // Check if any content is loaded, if not inflate stub
        viewModel.isError = true
        if (viewModel.watchedShows.isEmpty()) {
            if (parent_item_error == null) stub_your_library_error_view.viewVisible()
            parent_item_error?.viewVisible()
            loadImageBackground()
            RxView.clicks(button_item_error_retry)
                    .debounce(2L, TimeUnit.SECONDS)
                    .subscribe { onRetry() }
        } else {
            parent_layout_your_library.postDelayed({ errorSnackBar.show() }, 300L)
        }
    }

    override fun hideError() {
        parent_item_error?.viewGone()
        errorSnackBar.let {
            if (it.isShown) {
                it.dismiss()
            }
        }
        viewModel.isError = false
    }

    override fun showEmptyLibraryMessage() {
        if (parent_view_empty_message == null) stub_your_library_empty_message.viewVisible()
        button_layout_empty_message_feed.setOnClickListener { Navigator.startFeedActivity(this) }
        button_layout_empty_message_search.setOnClickListener { Navigator.startSearchActivity(this) }
        kenburns_view_show_frame.apply {
            viewInvisible()
            setImageBitmap(null)
        }
        view_watched_show_your_library.viewGone()
        loadImageBackground()
        invalidateOptionsMenu()
    }

    override fun onItemSelected(item: TvShowModel?, vararg views: View) {
        item?.let { Navigator.startTvShowDetails(this, it) }
    }

    override fun onCurrentItemChanged(viewHolder: RecyclerView.ViewHolder?, adapterPosition: Int) {
        if (viewHolder is YourLibraryDelegateAdapter.WatchedShowViewHolder) {
            if (isValidContextForGlide(this)) {
                Glide.with(this)
                        .asBitmap()
                        .load(viewModel.watchedShows[adapterPosition].tvShow?.imageMedium)
                        .into(applyWatchedShowDetails(viewModel.watchedShows[adapterPosition]))
                initKenBurnsView(viewModel.watchedShows[adapterPosition].tvShow?.imageMedium)
            }
            if ((adapterPosition + 1 == viewModel.watchedShows.size) and !viewModel.isError) {
                presenter.requestModel(++viewModel.page, false)
            }
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

    private fun initKenBurnsView(img: String?) = with(kenburns_view_show_frame) {
        GlideApp.with(this).load(img)
                .transform(MultiTransformation(GrayscaleTransformation(),
                        BlurTransformation(this@YourLibraryActivity, 1)))
                .into(kenburns_view_show_frame)
        startAnimation(AlphaAnimation(0.0f, 1f).apply {
            duration = 600
            fillAfter = true
        })
        kenburns_view_show_frame.setTransitionGenerator(RandomTransitionGenerator(10000, LinearInterpolator()))
    }

    private fun applyWatchedShowDetails(watchedTvShowViewModel: WatchedTvShowViewModel): SimpleTarget<Bitmap> {
        return object : SimpleTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap?, transition: Transition<in Bitmap>?) {
                resource?.let {
                    view_watched_show_your_library.setTvShowDetails(watchedTvShowViewModel)
                    view_watched_show_your_library.startBitmapFillingAnimation(bitmap = resource)
                }
            }
        }
    }

    override fun onRetry() = presenter.requestModel(page = viewModel.page, update = false)

    private fun setNavigationBarOptions() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        if (isLandscapeOrientation() and !isNavigationBarLandscape()) {
            with(text_view_your_library_watching_time) {
                setPadding(paddingStart, paddingTop, (paddingEnd + getNavigationBarSize().x), paddingBottom)
            }
            with(errorSnackBar.view) {
                setPadding(paddingStart, paddingTop, (paddingEnd + getNavigationBarSize().x), paddingBottom)
            }
        }
        if (!isLandscapeOrientation()) {
            with(text_view_your_library_watching_time) {
                setPadding(paddingStart, paddingTop, paddingEnd, (paddingBottom + getNavigationBarSize().y))
            }
        }
    }

    private fun createTipDialog(): AlertDialog = AlertDialog.Builder(this)
            .setIcon(R.drawable.all_ic_info)
            .setTitle(R.string.alert_dialog_tip_title)
            .setMessage(R.string.alert_dialog_your_library_tip_message)
            .setPositiveButton(R.string.alert_dialog_button_text) { p0, _ -> p0?.dismiss() }
            .create()

    private fun dismissDialog(dialog: Dialog?) = with(dialog) {
        this?.let {
            if (isShowing) {
                dismiss()
            }
        }
    }

    private companion object {
        val SWIPE_THRESHOLD = 0.1f
    }

}
