package pl.hypeapp.episodie.di.module

import dagger.Module
import dagger.Provides
import io.rx_cache2.internal.Disk
import io.rx_cache2.internal.RxCache
import io.rx_cache2.internal.encrypt.BuiltInEncryptor
import io.rx_cache2.internal.encrypt.FileEncryptor
import io.victoralbertos.jolyglot.GsonSpeaker
import pl.hypeapp.dataproviders.cache.CacheProviders
import pl.hypeapp.episodie.App
import javax.inject.Singleton

@Module
class CacheModule(private val app: App) {

    @Provides
    @Singleton
    fun provideGsonSpeaker(): GsonSpeaker {
        return GsonSpeaker()
    }

    @Provides
    @Singleton
    fun provideTvShowCacheProviders(gsonSpeaker: GsonSpeaker): CacheProviders {
        return RxCache.Builder()
                .persistence(app.filesDir, gsonSpeaker)
                .using(CacheProviders::class.java)
    }

    @Provides
    @Singleton
    fun provideDiskCache(gsonSpeaker: GsonSpeaker): Disk {
        return Disk(app.filesDir, FileEncryptor(BuiltInEncryptor()), gsonSpeaker)
    }

}
