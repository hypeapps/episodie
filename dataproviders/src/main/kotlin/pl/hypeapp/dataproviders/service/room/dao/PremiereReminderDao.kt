package pl.hypeapp.dataproviders.service.room.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Single
import pl.hypeapp.dataproviders.entity.room.PremiereReminderEntity

@Dao
interface PremiereReminderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertReminder(reminder: PremiereReminderEntity)

    @Query("SELECT * FROM premiere_reminders WHERE tv_show_id = :tvShowId")
    fun getReminderById(tvShowId: String): PremiereReminderEntity

    @Query("SELECT * FROM premiere_reminders WHERE tv_show_id = :tvShowId")
    fun getReminderSingleById(tvShowId: String): Single<PremiereReminderEntity>

    @Query("DELETE FROM premiere_reminders WHERE tv_show_id LIKE :tvShowId")
    fun deleteReminder(tvShowId: String)

}
