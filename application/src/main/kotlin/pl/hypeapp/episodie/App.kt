package pl.hypeapp.episodie

import android.app.Application
import com.exyui.android.debugbottle.components.DTInstaller
import okhttp3.OkHttpClient

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        injectDebugBottle()
    }

    private fun injectDebugBottle() {
        DTInstaller.install(this)
                .setBlockCanary(AppBlockCanaryContext(this))
                .setOkHttpClient(httpClient)
                .enable()
                .run()
    }

    companion object {
        val httpClient by lazy { OkHttpClient() }
    }

}