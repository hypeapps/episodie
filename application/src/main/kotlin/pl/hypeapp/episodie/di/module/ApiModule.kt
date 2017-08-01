package pl.hypeapp.episodie.di.module

import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import pl.hypeapp.dataproviders.service.api.BasicAuthInterceptor
import pl.hypeapp.dataproviders.service.api.EpisodieApi
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApiModule(private val endpoint: String,
                private val username: String,
                private val password: String) {

    @Provides
    @Singleton
    fun provideBasicAuthInterceptor(): BasicAuthInterceptor {
        return BasicAuthInterceptor(username, password)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(basicAuthInterceptor: BasicAuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(basicAuthInterceptor)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addNetworkInterceptor(StethoInterceptor()).build()
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
                .baseUrl(endpoint)
                .client(okHttpClient)
                .addCallAdapterFactory(callAdapterFactory)
                .addConverterFactory(gsonConverterFactory)
                .build()
                .create(EpisodieApi::class.java)
    }
}
