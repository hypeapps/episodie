package pl.hypeapp.domain.repository

import io.reactivex.Completable
import io.reactivex.Single
import pl.hypeapp.domain.model.SeasonTrackerModel

interface SeasonTrackerRepository {

    fun addSeasonToSeasonTracker(seasonTrackerModel: SeasonTrackerModel): Completable

    fun getSeasonTackerModel(): Single<SeasonTrackerModel>

    fun deleteSeasonTracker(): Completable

}
