package pl.hypeapp.dataproviders.datasource

import io.reactivex.Single
import pl.hypeapp.dataproviders.entity.room.RuntimeEntity

interface RuntimeDataStore {

    fun getUserFullRuntime(): Single<RuntimeEntity>

}
