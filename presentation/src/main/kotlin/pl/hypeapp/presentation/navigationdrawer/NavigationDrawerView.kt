package pl.hypeapp.presentation.navigationdrawer

import pl.hypeapp.presentation.base.View

interface NavigationDrawerView : View {

    fun setWatchingTime(watchingTime: Long)
    fun setOnClickListeners()
}
