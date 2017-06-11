package pl.hypeapp.episodie

import android.app.Application
import com.facebook.stetho.Stetho
import pl.hypeapp.episodie.di.components.AppComponent
import pl.hypeapp.episodie.di.components.DaggerAppComponent
import pl.hypeapp.episodie.di.module.AppModule
import pl.hypeapp.episodie.di.module.CacheModule
import pl.hypeapp.episodie.di.module.NetworkModule

class App : Application() {

    val component: AppComponent
        get() = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .cacheModule(CacheModule(this))
                .networkModule(NetworkModule("http://api.tvmaze.com/"))
                .build()

    override fun onCreate() {
        super.onCreate()
        initStetho()
    }

    private fun initStetho() {
        Stetho.initializeWithDefaults(this)
    }

}