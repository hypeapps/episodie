package pl.hypeapp.episodie.di.components

import android.content.Context
import dagger.Component
import pl.hypeapp.domain.executor.PostExecutionThread
import pl.hypeapp.domain.executor.ThreadExecutor
import pl.hypeapp.domain.repository.Repository
import pl.hypeapp.episodie.App
import pl.hypeapp.episodie.di.module.AppModule
import pl.hypeapp.episodie.di.module.CacheModule
import pl.hypeapp.episodie.di.module.NetworkModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, NetworkModule::class, CacheModule::class))
interface AppComponent {

    fun inject(app: App)

    val app: App

    fun context(): Context

    fun threadExecutor(): ThreadExecutor

    fun postExecutionThread(): PostExecutionThread

    fun repository(): Repository

}