package pl.hypeapp.episodie

import android.app.Application
import com.exyui.android.debugbottle.components.DTInstaller
import com.facebook.stetho.Stetho
import okhttp3.OkHttpClient

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        injectDebugBottle()
        initStetho()
    }

    private fun injectDebugBottle() {
        DTInstaller.install(this)
                .setBlockCanary(AppBlockCanaryContext(this))
                .setOkHttpClient(httpClient)
                .enable()
                .run()
    }

    private fun initStetho() {
        Stetho.initializeWithDefaults(this)
    }

    companion object {
        val httpClient by lazy { OkHttpClient() }
    }

}