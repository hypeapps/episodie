package pl.hypeapp.dataproviders.datasource

import io.reactivex.Single
import pl.hypeapp.dataproviders.entity.room.RuntimeEntity
import pl.hypeapp.dataproviders.service.room.RoomService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RuntimeDataSource @Inject constructor(private val roomService: RoomService) : RuntimeDataStore {

    override fun getUserFullRuntime(): Single<RuntimeEntity> {
        return roomService.runtimeDao.getUserFullRuntime()
    }

}
