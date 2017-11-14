package pl.hypeapp.dataproviders.datasource

import io.reactivex.Single
import pl.hypeapp.dataproviders.entity.room.SeasonTrackerEntity

interface SeasonTrackerDataStore {

    fun insertSeasonToSeasonTracker(seasonTrackerEntity: SeasonTrackerEntity)

    fun getSeasonTracker(): Single<SeasonTrackerEntity>

    fun deleteSeasonTracker()

}
