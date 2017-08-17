package pl.hypeapp.episodie.di.components

import android.content.Context
import dagger.Component
import pl.hypeapp.domain.executor.PostExecutionThread
import pl.hypeapp.domain.executor.ThreadExecutor
import pl.hypeapp.domain.repository.AllSeasonsRepository
import pl.hypeapp.domain.repository.MostPopularRepository
import pl.hypeapp.domain.repository.TopListRepository
import pl.hypeapp.episodie.App
import pl.hypeapp.episodie.di.module.ApiModule
import pl.hypeapp.episodie.di.module.AppModule
import pl.hypeapp.episodie.di.module.CacheModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, ApiModule::class, CacheModule::class))
interface AppComponent {

    fun inject(app: App)

    val app: App

    fun context(): Context

    fun threadExecutor(): ThreadExecutor

    fun postExecutionThread(): PostExecutionThread

    fun mostPopularRepository(): MostPopularRepository

    fun topListRepository(): TopListRepository

    fun allSeasonsRepository(): AllSeasonsRepository

}
