package pl.hypeapp.episodie.ui

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import pl.hypeapp.dataproviders.entity.room.WatchedSeasonEntity
import pl.hypeapp.dataproviders.entity.room.WatchedTvShowEntity
import pl.hypeapp.dataproviders.service.room.RoomService
import pl.hypeapp.episodie.BuildConfig
import java.util.logging.Logger

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class,
        application = Test1.ApplicationStub::class,
        manifest = Config.NONE,
        sdk = intArrayOf(21))
class Test1 {

    private lateinit var db: RoomService

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(context(), RoomService::class.java)
                .allowMainThreadQueries()
                .build()
    }

    @After
    fun close() {
        db.close()
    }

    fun context(): Context {
        return RuntimeEnvironment.application
    }

    @Test
    fun hello() {
        db.watchedShowDao.insertTvShow(WatchedTvShowEntity(tvShowId = "T1", title = "XD", seasons = arrayListOf(WatchedSeasonEntity(seasonId = "S1", tvShowId = "T1"))))
//        db.watchedShowDao.insertSeason(WatchedSeasonEntity(seasonId = "S1", tvShowId = "T1"))
//        db.watchedShowDao.insertEpisode(WatchedEpisodeEntity(episodeId = "E1", seasonId = "S1", tvShowId = "T1"))
//        Logger.getAnonymousLogger().info ("" + db.watchedShowDao.getTvShowById("T1").blockingGet().title)
        Logger.getAnonymousLogger().info("" + db.watchedShowDao.getSeasonsByTvShowId("T1").blockingGet().first().seasonId)
//        Logger.getAnonymousLogger().info ("" + db.watchedShowDao.getEpisodesBySeasonId("S1").blockingGet().first().episodeId)
//        db.watchedShowDao.deleteTvShow(WatchedTvShowEntity("T1"))
//        db.watchedShowDao.deleteSeason(WatchedSeasonEntity(seasonId = "S1"))
//        Logger.getAnonymousLogger().info ("" + db.watchedShowDao.getTvShowById("T1").blockingGet().tvShowId)
//        Logger.getAnonymousLogger().info ("" + db.watchedShowDao.getSeasonsByTvShowId("T1").blockingGet().first().seasonId)
//        Logger.getAnonymousLogger().info ("" + db.watchedShowDao.getEpisodesBySeasonId("S1").blockingGet().first().episodeId)

        assert(true)
    }

    internal class ApplicationStub : Application()
}
