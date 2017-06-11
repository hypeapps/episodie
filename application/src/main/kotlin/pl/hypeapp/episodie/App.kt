package pl.hypeapp.episodie

import android.app.Application
import com.facebook.stetho.Stetho
import pl.hypeapp.episodie.di.components.AppComponent
import pl.hypeapp.episodie.di.components.DaggerAppComponent
import pl.hypeapp.episodie.di.module.ApiModule
import pl.hypeapp.episodie.di.module.AppModule
import pl.hypeapp.episodie.di.module.CacheModule

class App : Application() {

    val component: AppComponent
        get() = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .cacheModule(CacheModule(this))
                .apiModule(ApiModule(URL_API_ENDPOINT, USERNAME, PASSWORD))
                .build()

    override fun onCreate() {
        super.onCreate()
        initStetho()
    }

    private fun initStetho() {
        Stetho.initializeWithDefaults(this)
    }

    companion object {
        val URL_API_ENDPOINT = "https://episodie.ct8.pl/api/"
        // YOU NEED TO SPECIFY OWN USERNAME AND PASSWORD IN gradle.properties
        val USERNAME = BuildConfig.USERNAME_EPISODIE_API
        val PASSWORD = BuildConfig.PASSWORD_EPISODIE_API
    }
}