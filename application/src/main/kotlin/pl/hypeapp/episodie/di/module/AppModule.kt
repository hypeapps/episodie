package pl.hypeapp.episodie.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import pl.hypeapp.dataproviders.executor.JobExecutor
import pl.hypeapp.dataproviders.repository.AllSeasonsDataRepository
import pl.hypeapp.dataproviders.repository.MostPopularDataRepository
import pl.hypeapp.dataproviders.repository.TopListDataRepository
import pl.hypeapp.domain.executor.PostExecutionThread
import pl.hypeapp.domain.executor.ThreadExecutor
import pl.hypeapp.domain.repository.AllSeasonsRepository
import pl.hypeapp.domain.repository.MostPopularRepository
import pl.hypeapp.domain.repository.TopListRepository
import pl.hypeapp.episodie.App
import pl.hypeapp.episodie.UIThread
import javax.inject.Singleton

@Module
class AppModule(private val app: App) {

    @Provides
    @Singleton
    fun application(): App {
        return app
    }

    @Provides
    @Singleton
    fun provideApplicationContext(): Context {
        return app
    }

    @Provides
    @Singleton
    fun provideThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor {
        return jobExecutor
    }

    @Provides
    @Singleton
    fun providePostExecutionThread(uiThread: UIThread): PostExecutionThread {
        return uiThread
    }

    @Provides
    @Singleton
    fun provideMostPopularRepository(mostPopularDataRepository: MostPopularDataRepository): MostPopularRepository {
        return mostPopularDataRepository
    }

    @Provides
    @Singleton
    fun provideTopListRepository(topListDataRepository: TopListDataRepository): TopListRepository {
        return topListDataRepository
    }

    @Provides
    @Singleton
    fun provideAllSeasonsRepository(allSeasonsDataRepository: AllSeasonsDataRepository): AllSeasonsRepository {
        return allSeasonsDataRepository
    }

}
