package pl.hypeapp.episodie.di.module

import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import pl.hypeapp.dataproviders.service.api.EpisodieApi
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule(private val apiEndpoint: String) {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().addNetworkInterceptor(StethoInterceptor()).build()
    }

    @Provides
    @Singleton
    fun provideCallAdapterFactory(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.create()
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideEpisodieApi(okHttpClient: OkHttpClient,
                           callAdapterFactory: RxJava2CallAdapterFactory,
                           gsonConverterFactory: GsonConverterFactory): EpisodieApi {
        return Retrofit.Builder()
                .baseUrl(apiEndpoint)
                .client(okHttpClient)
                .addCallAdapterFactory(callAdapterFactory)
                .addConverterFactory(gsonConverterFactory)
                .build()
                .create(EpisodieApi::class.java)
    }
}
