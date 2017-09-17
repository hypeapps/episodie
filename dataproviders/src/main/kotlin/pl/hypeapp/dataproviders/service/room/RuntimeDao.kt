package pl.hypeapp.dataproviders.service.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import io.reactivex.Single
import pl.hypeapp.dataproviders.entity.room.RuntimeEntity

@Dao
interface RuntimeDao {

    @Query("SELECT sum(runtime) as runtime FROM watched_episodes")
    fun getUserFullRuntime(): Single<RuntimeEntity>

}
