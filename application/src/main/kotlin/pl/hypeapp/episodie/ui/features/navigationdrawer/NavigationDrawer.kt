package pl.hypeapp.episodie.ui.features.navigationdrawer

import android.app.Activity
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.Toolbar
import android.widget.TextView
import butterknife.BindView
import butterknife.BindViews
import butterknife.ButterKnife
import com.andexert.library.RippleView
import com.doctoror.particlesdrawable.ParticlesDrawable
import com.facebook.device.yearclass.YearClass
import com.yarolegovich.slidingrootnav.SlidingRootNav
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder
import com.yarolegovich.slidingrootnav.callback.DragListener
import com.yarolegovich.slidingrootnav.callback.DragStateListener
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import pl.hypeapp.episodie.R
import pl.hypeapp.episodie.extensions.getFullRuntimeFormatted
import pl.hypeapp.episodie.navigation.Navigator
import pl.hypeapp.episodie.ui.features.mainfeed.MainFeedActivity
import pl.hypeapp.episodie.ui.features.search.SearchActivity
import pl.hypeapp.episodie.ui.features.seasontracker.SeasonTrackerActivity
import pl.hypeapp.episodie.ui.features.timecalculator.TimeCalculatorActivity
import pl.hypeapp.episodie.ui.features.yourlibrary.YourLibraryActivity
import pl.hypeapp.episodie.ui.widget.DrawerMenuItemView
import pl.hypeapp.presentation.navigationdrawer.NavigationDrawerPresenter
import pl.hypeapp.presentation.navigationdrawer.NavigationDrawerView
import javax.inject.Inject

class NavigationDrawer @Inject constructor(val activity: Activity,
                                           val presenter: NavigationDrawerPresenter)
    : LifecycleObserver, NavigationDrawerView, DragStateListener, DragListener {

    private val publishDragSubject: PublishSubject<Float> = PublishSubject.create()

    private val slidingRootNavigatorBuilder: SlidingRootNavBuilder = SlidingRootNavBuilder(activity)
            .withMenuLayout(R.layout.drawer_navigation)
            .addDragStateListener(this)
            .addDragListener(this)

    private lateinit var slidingRootNavigator: SlidingRootNav

    private val particlesDrawable: ParticlesDrawable = ParticlesDrawable()

    @BindViews(R.id.drawer_menu_item_feed,
            R.id.drawer_menu_item_search,
            R.id.drawer_menu_item_watched,
            R.id.drawer_menu_item_time_calculator,
            R.id.drawer_menu_item_season_tracker)
    lateinit var drawerItems: List<DrawerMenuItemView>

    @BindView(R.id.text_view_drawer_watching_time)
    lateinit var watchingTimeTextView: TextView

    init {
        particlesDrawable.lineDistance = 270f
    }

    fun initWithToolbar(toolbar: Toolbar?) {
        slidingRootNavigator = slidingRootNavigatorBuilder.withToolbarMenuToggle(toolbar).inject()
        if (YearClass.get(activity.applicationContext) >= YearClass.CLASS_2013) {
            slidingRootNavigator.layout.findViewById<ConstraintLayout>(R.id.constraint_layout_drawer).background = particlesDrawable
        }
    }

    fun init() {
        slidingRootNavigator = slidingRootNavigatorBuilder.inject()
        slidingRootNavigator.layout.findViewById<ConstraintLayout>(R.id.constraint_layout_drawer).background = particlesDrawable
    }

    fun toggleDrawer() {
        if (slidingRootNavigator.isMenuClosed) {
            slidingRootNavigator.openMenu(true)
        } else {
            slidingRootNavigator.closeMenu(true)
        }
    }

    override fun onDragEnd(isMenuOpened: Boolean) {
        if (!isMenuOpened) {
            particlesDrawable.stop()
        }
    }

    override fun onDragStart() {
        if (!particlesDrawable.isRunning)
            particlesDrawable.start()
    }

    override fun onDrag(progress: Float) = publishDragSubject.onNext(progress)

    // Need to exhibit on drag progress for fragments controlling drawer/hamburger arrow animation.
    fun onDrag(): Observable<Float>? = publishDragSubject

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        ButterKnife.bind(this, activity)
        setProperActiveItem(activity)
        presenter.onAttachView(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        presenter.onDetachView()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        if (particlesDrawable.isRunning) {
            particlesDrawable.stop()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        // Workaround for freezing when orientation change
        if (slidingRootNavigator.isMenuClosed) {
            slidingRootNavigator.closeMenu()
        }
        if (slidingRootNavigator.isMenuOpened) {
            particlesDrawable.start()
        }
    }

    override fun setWatchingTime(watchingTime: Long) {
        watchingTimeTextView.text = String.format(activity.getString(R.string.watching_time_format),
                getFullRuntimeFormatted(activity.resources, watchingTime))
    }

    override fun setOnClickListeners() = with(activity) {
        findViewById<RippleView>(R.id.ripple_view_drawer_menu_item_feed).setOnRippleCompleteListener {
            if (drawerItems[FEED_ITEM_INDEX].isActive) {
                slidingRootNavigator.closeMenu()
            } else {
                setInactiveAllItems()
                drawerItems[FEED_ITEM_INDEX].isActive = true
                Navigator.startFeedActivity(this)
            }
        }
        findViewById<RippleView>(R.id.ripple_view_drawer_menu_item_search).setOnRippleCompleteListener {
            if (drawerItems[SEARCH_ITEM_INDEX].isActive) {
                slidingRootNavigator.closeMenu()
            } else {
                setInactiveAllItems()
                drawerItems[SEARCH_ITEM_INDEX].isActive = true
                Navigator.startSearchActivity(this)
            }
        }
        findViewById<RippleView>(R.id.ripple_view_drawer_menu_item_watched).setOnRippleCompleteListener {
            if (drawerItems[YOUR_LIBRARY_ITEM_INDEX].isActive) {
                slidingRootNavigator.closeMenu()
            } else {
                setInactiveAllItems()
                drawerItems[YOUR_LIBRARY_ITEM_INDEX].isActive = true
                Navigator.startYourLibraryActivity(this)
            }
        }
        findViewById<RippleView>(R.id.ripple_view_drawer_menu_item_time_calculator).setOnRippleCompleteListener {
            if (drawerItems[TIME_CALCULATOR_ITEM_INDEX].isActive) {
                slidingRootNavigator.closeMenu()
            } else {
                setInactiveAllItems()
                drawerItems[TIME_CALCULATOR_ITEM_INDEX].isActive = true
                Navigator.startTimeCalculatorActivity(this)
            }
        }
        findViewById<RippleView>(R.id.ripple_view_drawer_menu_item_season_tracker).setOnRippleCompleteListener {
            if (drawerItems[SEASON_TRACKER_ITEM_INDEX].isActive) {
                slidingRootNavigator.closeMenu()
            } else {
                setInactiveAllItems()
                drawerItems[SEASON_TRACKER_ITEM_INDEX].isActive = true
                Navigator.startSeasonTrackerActivity(this)
            }
        }
    }

    private fun setProperActiveItem(activity: Activity) {
        setInactiveAllItems()
        when (activity) {
            is MainFeedActivity -> activity.findViewById<DrawerMenuItemView>(R.id.drawer_menu_item_feed).isActive = true
            is SearchActivity -> activity.findViewById<DrawerMenuItemView>(R.id.drawer_menu_item_search).isActive = true
            is YourLibraryActivity -> activity.findViewById<DrawerMenuItemView>(R.id.drawer_menu_item_watched).isActive = true
            is TimeCalculatorActivity -> activity.findViewById<DrawerMenuItemView>(R.id.drawer_menu_item_time_calculator).isActive = true
            is SeasonTrackerActivity -> activity.findViewById<DrawerMenuItemView>(R.id.drawer_menu_item_season_tracker).isActive = true
        }
    }

    private fun setInactiveAllItems() = drawerItems.forEach { it.isActive = false }

    private companion object {
        const val FEED_ITEM_INDEX = 0
        const val SEARCH_ITEM_INDEX = 1
        const val YOUR_LIBRARY_ITEM_INDEX = 2
        const val TIME_CALCULATOR_ITEM_INDEX = 3
        const val SEASON_TRACKER_ITEM_INDEX = 4
    }

}
