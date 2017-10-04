package pl.hypeapp.episodie.di.components

import android.content.Context
import dagger.Component
import pl.hypeapp.domain.executor.PostExecutionThread
import pl.hypeapp.domain.executor.ThreadExecutor
import pl.hypeapp.domain.repository.*
import pl.hypeapp.episodie.App
import pl.hypeapp.episodie.di.module.ApiModule
import pl.hypeapp.episodie.di.module.AppModule
import pl.hypeapp.episodie.di.module.CacheModule
import pl.hypeapp.episodie.di.module.DatabaseModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, ApiModule::class, CacheModule::class, DatabaseModule::class))
interface AppComponent {

    fun inject(app: App)

    val app: App

    fun context(): Context

    fun threadExecutor(): ThreadExecutor

    fun postExecutionThread(): PostExecutionThread

    fun mostPopularRepository(): MostPopularRepository

    fun topListRepository(): TopListRepository

    fun allSeasonsRepository(): AllSeasonsRepository

    fun addToWatchedRepository(): WatchedRepository

    fun runtimeRepository(): RuntimeRepository

    fun searchRepository(): SearchRepository

    fun tvShowRepository(): TvShowRepository

}
