package pl.hypeapp.dataproviders.service.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import pl.hypeapp.dataproviders.entity.room.WatchedEpisodeEntity

@Database(entities = arrayOf(WatchedEpisodeEntity::class), version = 1)
abstract class RoomService : RoomDatabase() {

    abstract val watchedEpisodeDao: WatchedEpisodeDao

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
