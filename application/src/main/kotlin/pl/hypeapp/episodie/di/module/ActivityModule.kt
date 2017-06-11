package pl.hypeapp.episodie.di.module

import android.app.Activity
import dagger.Module
import dagger.Provides
import pl.hypeapp.episodie.di.scope.PerActivity

@Module
class ActivityModule(private val mainActivity: Activity) {

    @Provides
    @PerActivity
    internal fun provideActivity(): Activity {
        return this.mainActivity
    }
}
