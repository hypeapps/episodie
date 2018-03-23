package pl.hypeapp.dataproviders.service.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import pl.hypeapp.dataproviders.entity.room.*
import pl.hypeapp.dataproviders.service.room.dao.*

@Database(entities = [
    (WatchedEpisodeEntity::class),
    (SeasonTrackerEntity::class),
    (EpisodeReminderEntity::class),
    (PremiereReminderEntity::class),
    (WatchedTvShowEntity::class),
    (WatchedSeasonEntity::class)],
        version = 1)
@TypeConverters(Converters::class)
abstract class RoomService : RoomDatabase() {

    abstract val watchedEpisodeDao: WatchedEpisodeDao

    abstract val userStatsDao: UserStatsDao

    abstract val seasonTrackerDao: SeasonTrackerDao

    abstract val episodeReminderDao: EpisodeReminderDao

    abstract val premiereReminderDao: PremiereReminderDao

    abstract val watchedShowDao: WatchedShowDao

    companion object {

        @Volatile private var INSTANCE: RoomService? = null

        fun getInstance(context: Context, dbName: String): RoomService =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase(context, dbName).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context, dbName: String) =
                Room.databaseBuilder(context.applicationContext, RoomService::class.java, dbName)
                        .build()
    }

}
