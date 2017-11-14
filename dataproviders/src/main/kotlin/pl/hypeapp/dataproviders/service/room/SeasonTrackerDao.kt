package pl.hypeapp.dataproviders.service.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import io.reactivex.Single
import pl.hypeapp.dataproviders.entity.room.SeasonTrackerEntity

@Dao
interface SeasonTrackerDao {

    @Insert(onConflict = REPLACE)
    fun insertSeasonToSeasonTracker(tvShow: SeasonTrackerEntity)

    @Query("SELECT * from season_tracker")
    fun getSeasonTracker(): Single<SeasonTrackerEntity>

    @Query("DELETE FROM season_tracker")
    fun clearAll()

}
