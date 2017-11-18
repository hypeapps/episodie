package pl.hypeapp.dataproviders.entity.room

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "premiere_reminders")
data class PremiereReminderEntity(@PrimaryKey
                                  @ColumnInfo(name = "tv_show_id")
                                  var tvShowId: String = "",
                                  var jobId: Int = 0,
                                  var timestamp: Long = 0L)
