package pl.hypeapp.episodie.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import pl.hypeapp.dataproviders.executor.JobExecutor
import pl.hypeapp.dataproviders.repository.*
import pl.hypeapp.domain.executor.PostExecutionThread
import pl.hypeapp.domain.executor.ThreadExecutor
import pl.hypeapp.domain.repository.*
import pl.hypeapp.episodie.App
import pl.hypeapp.episodie.Prefs
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
    fun providePrefs(applicationContext: Context): Prefs = Prefs(applicationContext)

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

    @Provides
    @Singleton
    fun provideWatchedShowRepository(watchedShowDataRepository: WatchedShowDataRepository): WatchedShowRepository {
        return watchedShowDataRepository
    }

    @Provides
    @Singleton
    fun provideRuntimeRepository(userStatsDataRepository: UserStatsDataRepository): UserStatsRepository {
        return userStatsDataRepository
    }

    @Provides
    @Singleton
    fun provideSearchRepository(searchDataRepository: SearchDataRepository): SearchRepository {
        return searchDataRepository
    }

    @Provides
    @Singleton
    fun provideTvShowRepository(tvShowDataRepository: TvShowDataRepository): TvShowRepository {
        return tvShowDataRepository
    }

    @Provides
    @Singleton
    fun provideSeasonTrackerRepository(seasonTrackerDataRepository: SeasonTrackerDataRepository): SeasonTrackerRepository {
        return seasonTrackerDataRepository
    }

    @Provides
    @Singleton
    fun provideEpisodeReminderRepository(episodeReminderDataRepository: EpisodeReminderDataRepository): EpisodeReminderRepository {
        return episodeReminderDataRepository
    }

    @Provides
    @Singleton
    fun providePremiereDatesRepository(premiereDatesDataRepository: PremiereDatesDataRepository): PremiereDatesRepository {
        return premiereDatesDataRepository
    }

}
