package pl.hypeapp.episodie

import android.app.Application
import android.content.Context
import android.util.Base64
import android.util.Log
import com.facebook.stetho.Stetho
import com.frogermcs.androiddevmetrics.AndroidDevMetrics
import com.github.moduth.blockcanary.BlockCanary
import com.github.moduth.blockcanary.BlockCanaryContext
import com.github.moduth.blockcanary.internal.BlockInfo
import com.squareup.leakcanary.LeakCanary
import pl.hypeapp.episodie.di.components.AppComponent
import pl.hypeapp.episodie.di.module.ApiModule
import pl.hypeapp.episodie.di.module.AppModule
import pl.hypeapp.episodie.di.module.CacheModule
import pl.hypeapp.episodie.di.module.DatabaseModule
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import java.nio.charset.Charset

class App : Application() {

    val component: AppComponent
        get() = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .cacheModule(CacheModule(this))
                .apiModule(ApiModule(URL_API_ENDPOINT, USERNAME, PASSWORD))
                .databaseModule(DatabaseModule(this, DATABASE_NAME))
                .build()

    override fun onCreate() {
        super.onCreate()
        initStetho()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)
        BlockCanary.install(this, AppBlockCanaryContext()).start()
        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/coolvetica.ttf")
                .build())
        AndroidDevMetrics.initWith(this)
    }

    private fun initStetho() {
        Stetho.initializeWithDefaults(this)
    }

    companion object {
        val URL_API_ENDPOINT = "https://episodie.ct8.pl/api/"
        val USERNAME = BuildConfig.USERNAME_EPISODIE_API
        val PASSWORD = String(Base64.decode(BuildConfig.PASSWORD_EPISODIE_API, Base64.DEFAULT), Charset.defaultCharset())
        val DATABASE_NAME = "episodie_db"
    }

}

class AppBlockCanaryContext : BlockCanaryContext() {

    override fun onBlock(context: Context?, blockInfo: BlockInfo?) {
        super.onBlock(context, blockInfo)
        blockInfo?.threadStackEntries?.forEach { Log.e("BLOCK CANARY ", it) }

    }

    override fun providePath(): String {
        return "/blockcanary/performance"
    }
}
