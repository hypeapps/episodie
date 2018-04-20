package pl.hypeapp.dataproviders.service.room.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Single
import pl.hypeapp.dataproviders.entity.room.EpisodeReminderEntity

@Dao
interface EpisodeReminderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertReminders(reminders: List<EpisodeReminderEntity>)

    @Query("SELECT * FROM episode_reminders")
    fun getReminders(): Single<List<EpisodeReminderEntity>>

    @Query("DELETE FROM episode_reminders")
    fun deleteAll()

}
