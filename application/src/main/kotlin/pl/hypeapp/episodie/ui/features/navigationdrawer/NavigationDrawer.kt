package pl.hypeapp.episodie.ui.features.navigationdrawer

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.util.Log
import android.widget.TextView
import com.yarolegovich.slidingrootnav.SlidingRootNav
import com.yarolegovich.slidingrootnav.SlidingRootNavLayout
import pl.hypeapp.episodie.R
import pl.hypeapp.presentation.navigationdrawer.NavigationDrawerView

class NavigationDrawer(val slidingRootNav: SlidingRootNav) : LifecycleObserver, NavigationDrawerView {

    override fun showError() {
        TODO("not implemented")
    }

    val slidingRootNavLayout: SlidingRootNavLayout = slidingRootNav.layout

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        Log.e("EVENT", "ON START")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        Log.e("EVENT", "ON CREATE")
        change()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        Log.e("EVENT", "ON STOP")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        Log.e("EVENT", "ON DESTROY")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        Log.e("EVENT", "ON PAUSE")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        Log.e("EVENT", "ON RESUME")
    }

    fun change() {
        val slid: SlidingRootNavLayout = slidingRootNav.layout
        val text = slid.findViewById<TextView>(R.id.chuj)
        text.setOnClickListener({ text.text = "ELO" })
    }

}
