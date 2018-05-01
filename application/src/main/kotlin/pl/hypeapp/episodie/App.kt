package pl.hypeapp.episodie

import android.support.multidex.MultiDexApplication
import android.util.Base64
import com.evernote.android.job.JobManager
import pl.hypeapp.episodie.di.components.AppComponent
import pl.hypeapp.episodie.di.components.DaggerAppComponent
import pl.hypeapp.episodie.di.module.ApiModule
import pl.hypeapp.episodie.di.module.AppModule
import pl.hypeapp.episodie.di.module.CacheModule
import pl.hypeapp.episodie.di.module.DatabaseModule
import pl.hypeapp.episodie.job.ReminderJobCreator
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import java.nio.charset.Charset

class App : MultiDexApplication() {

    val component: AppComponent
        get() = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .cacheModule(CacheModule(this))
                .apiModule(ApiModule(URL_API_ENDPOINT, USERNAME, PASSWORD))
                .databaseModule(DatabaseModule(this, DATABASE_NAME))
                .build()

    override fun onCreate() {
        super.onCreate()
        JobManager.create(this).addJobCreator(ReminderJobCreator(component.episodeReminderEngine()))
        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/coolvetica.ttf")
                .build())
    }

    companion object {
        const val URL_API_ENDPOINT = "https://episodie.ct8.pl/api/"
        const val USERNAME = BuildConfig.USERNAME_EPISODIE_API
        val PASSWORD = String(Base64.decode(BuildConfig.PASSWORD_EPISODIE_API, Base64.DEFAULT), Charset.defaultCharset())
        const val DATABASE_NAME = "episodie_db"
    }

}
