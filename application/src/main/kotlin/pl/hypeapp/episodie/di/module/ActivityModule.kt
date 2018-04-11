package pl.hypeapp.episodie.di.module

import android.app.Activity
import dagger.Module
import dagger.Provides
import pl.hypeapp.episodie.R
import pl.hypeapp.episodie.di.scope.PerActivity
import pl.hypeapp.episodie.ui.features.navigationdrawer.NavigationDrawer
import pl.hypeapp.presentation.navigationdrawer.NavigationDrawerPresenter

@Module
class ActivityModule(private val activity: Activity) {

    @Provides
    @PerActivity
    fun provideNavigationDrawer(presenter: NavigationDrawerPresenter): NavigationDrawer {
        return NavigationDrawer(activity, presenter)
    }

}
