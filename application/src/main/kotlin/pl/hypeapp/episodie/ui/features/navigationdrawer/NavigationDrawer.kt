package pl.hypeapp.episodie.ui.features.navigationdrawer

import android.app.Activity
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.Toolbar
import android.util.Log
import butterknife.ButterKnife
import com.doctoror.particlesdrawable.ParticlesDrawable
import com.yarolegovich.slidingrootnav.SlidingRootNav
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder
import com.yarolegovich.slidingrootnav.callback.DragListener
import com.yarolegovich.slidingrootnav.callback.DragStateListener
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import pl.hypeapp.episodie.R
import pl.hypeapp.presentation.navigationdrawer.NavigationDrawerView

class NavigationDrawer(val activity: Activity, toolbar: Toolbar) : LifecycleObserver, NavigationDrawerView,
        DragStateListener, DragListener {

//    @BindView(R.id.drawer_menu_item_feed)
//    lateinit var feedItem: DrawerMenuItemView

    private val publishDragSubject: PublishSubject<Float> = PublishSubject.create()

    private val slidingRootNavigator: SlidingRootNav = SlidingRootNavBuilder(activity)
            .withToolbarMenuToggle(toolbar)
            .withMenuLayout(R.layout.drawer_navigation)
            .addDragStateListener(this)
            .addDragListener(this)
            .inject()

    private val particlesDrawable: ParticlesDrawable = ParticlesDrawable()

    init {
        particlesDrawable.lineDistance = 270f
        slidingRootNavigator.layout.findViewById<ConstraintLayout>(R.id.constraint_layout_drawer).background = particlesDrawable
//        slidingRootNavigator.layout.findViewById<ConstraintLayout>(R.id.constraint_layout_drawer).visibility = View.GONE
        Log.e("line tempScroll", " " + particlesDrawable.lineDistance)
    }

    fun toggleDrawer() {
        if (slidingRootNavigator.isMenuHidden) {
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

    override fun onDrag(progress: Float) {
        publishDragSubject.onNext(progress)
    }

    // Need to exhibit on drag progress for fragments controlling drawer/hamburger arrow animation.
    fun onDrag(): Observable<Float>? = publishDragSubject

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStart() {
        Log.e("EVENT", "ON START")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun onCreate() {
        Log.e("EVENT", "ON CREATE")
        ButterKnife.bind(this, activity)
//        feedItem.setActive()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        Log.e("EVENT", "ON STOP")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun onDestroy() {
        Log.e("EVENT", "ON DESTROY")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private fun onPause() {
        if (particlesDrawable.isRunning) {
            particlesDrawable.stop()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun onResume() {
        if (!slidingRootNavigator.isMenuHidden) {
            particlesDrawable.start()
        }
    }

}
