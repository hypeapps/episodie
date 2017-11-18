package pl.hypeapp.episodie.di.components

import android.content.Context
import dagger.Component
import pl.hypeapp.domain.executor.PostExecutionThread
import pl.hypeapp.domain.executor.ThreadExecutor
import pl.hypeapp.domain.repository.*
import pl.hypeapp.episodie.App
import pl.hypeapp.episodie.Prefs
import pl.hypeapp.episodie.di.module.ApiModule
import pl.hypeapp.episodie.di.module.AppModule
import pl.hypeapp.episodie.di.module.CacheModule
import pl.hypeapp.episodie.di.module.DatabaseModule
import pl.hypeapp.episodie.job.episodereminder.EpisodeReminderEngine
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, ApiModule::class, CacheModule::class, DatabaseModule::class))
interface AppComponent {

    fun inject(app: App)

    val app: App

    fun context(): Context

    fun threadExecutor(): ThreadExecutor

    fun postExecutionThread(): PostExecutionThread

    fun prefs(): Prefs

    fun mostPopularRepository(): MostPopularRepository

    fun topListRepository(): TopListRepository

    fun allSeasonsRepository(): AllSeasonsRepository

    fun addToWatchedRepository(): WatchedRepository

    fun runtimeRepository(): RuntimeRepository

    fun searchRepository(): SearchRepository

    fun tvShowRepository(): TvShowRepository

    fun seasonTrackerRepository(): SeasonTrackerRepository

    fun premiereDatesRepository(): PremiereDatesRepository

    fun episodeReminderRepository(): EpisodeReminderRepository

    fun episodeReminderEngine(): EpisodeReminderEngine

}
