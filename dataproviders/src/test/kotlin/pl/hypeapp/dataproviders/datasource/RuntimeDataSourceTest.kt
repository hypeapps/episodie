package pl.hypeapp.dataproviders.datasource

import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import pl.hypeapp.dataproviders.entity.room.RuntimeEntity
import pl.hypeapp.dataproviders.service.room.RoomService

class RuntimeDataSourceTest {

    private lateinit var runtimeDataSource: RuntimeDataSource

    private var roomService: RoomService = mock()

    @Before
    fun setUp() {
        runtimeDataSource = RuntimeDataSource(roomService)
    }

    @Test
    fun `should get user runtime`() {
        val runtimeEntity: Single<RuntimeEntity> = Single.just(RuntimeEntity(11))
        given(roomService.runtimeDao).willReturn(mock())
        given(roomService.runtimeDao.getUserFullRuntime()).willReturn(runtimeEntity)

        runtimeDataSource.getUserFullRuntime()

        verify(roomService.runtimeDao).getUserFullRuntime()
    }

}
