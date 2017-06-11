package pl.hypeapp.episodie.di.module

import dagger.Module
import dagger.Provides
import io.rx_cache2.internal.RxCache
import io.victoralbertos.jolyglot.GsonSpeaker
import pl.hypeapp.dataproviders.cache.TvShowCacheProviders
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
    fun provideTvShowCacheProviders(gsonSpeaker: GsonSpeaker): TvShowCacheProviders {
        return RxCache.Builder()
                .persistence(app.filesDir, gsonSpeaker)
                .using(TvShowCacheProviders::class.java)
    }

}
