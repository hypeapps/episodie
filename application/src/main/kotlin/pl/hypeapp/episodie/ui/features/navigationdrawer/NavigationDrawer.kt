package pl.hypeapp.episodie.ui.features.navigationdrawer

import android.app.Activity
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.Toolbar
import butterknife.BindViews
import butterknife.ButterKnife
import butterknife.OnClick
import com.doctoror.particlesdrawable.ParticlesDrawable
import com.yarolegovich.slidingrootnav.SlidingRootNav
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder
import com.yarolegovich.slidingrootnav.callback.DragListener
import com.yarolegovich.slidingrootnav.callback.DragStateListener
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import pl.hypeapp.episodie.R
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

//    @Inject
//    lateinit var presenter: NavigationDrawerPresenter

    @BindViews(R.id.drawer_menu_item_feed, R.id.drawer_menu_item_search, R.id.drawer_menu_item_time_calculator,
            R.id.drawer_menu_item_watched, R.id.drawer_menu_item_season_tracker)
    lateinit var drawerItems: List<DrawerMenuItemView>

    init {
        particlesDrawable.lineDistance = 270f
    }

    fun initWithToolbar(toolbar: Toolbar?) {
        slidingRootNavigator = slidingRootNavigatorBuilder.withToolbarMenuToggle(toolbar).inject()
        slidingRootNavigator.layout.findViewById<ConstraintLayout>(R.id.constraint_layout_drawer).background = particlesDrawable
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
    fun onDestroy() = presenter.onDetachView()

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        if (particlesDrawable.isRunning) {
            particlesDrawable.stop()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        if (slidingRootNavigator.isMenuOpened) {
            particlesDrawable.start()
        }
    }

    @OnClick(R.id.drawer_menu_item_feed)
    fun navigateToFeed() = Navigator.startFeedActivity(activity)

    @OnClick(R.id.drawer_menu_item_search)
    fun navigateToSearch() = Navigator.startSearchActivity(activity)

    @OnClick(R.id.drawer_menu_item_time_calculator)
    fun navigateToTimeCalculator() = Navigator.startTimeCalculatorActivity(activity)

    @OnClick(R.id.drawer_menu_item_season_tracker)
    fun navigateToSeasonTracker() = Navigator.startSeasonTrackerActivity(activity)

    @OnClick(R.id.drawer_menu_item_watched)
    fun navigateToToYourLibrary() = Navigator.startYourLibraryActivity(activity)

    private fun setProperActiveItem(activity: Activity) {
        setInactiveAllItems()
        when (activity) {
            is MainFeedActivity -> activity.findViewById<DrawerMenuItemView>(R.id.drawer_menu_item_feed).setActive()
            is SearchActivity -> activity.findViewById<DrawerMenuItemView>(R.id.drawer_menu_item_search).setActive()
            is YourLibraryActivity -> activity.findViewById<DrawerMenuItemView>(R.id.drawer_menu_item_watched).setActive()
            is TimeCalculatorActivity -> activity.findViewById<DrawerMenuItemView>(R.id.drawer_menu_item_time_calculator).setActive()
            is SeasonTrackerActivity -> activity.findViewById<DrawerMenuItemView>(R.id.drawer_menu_item_season_tracker).setActive()
        }
    }

    private fun setInactiveAllItems() = drawerItems.forEach { it.setInactive() }

}
