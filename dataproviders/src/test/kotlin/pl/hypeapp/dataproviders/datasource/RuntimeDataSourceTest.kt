package pl.hypeapp.dataproviders.datasource

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import pl.hypeapp.dataproviders.service.room.RoomService

class RuntimeDataSourceTest {

    private lateinit var userStatsDataSource: UserStatsDataSource

    private var roomService: RoomService = mock()

    @Before
    fun setUp() {
        userStatsDataSource = UserStatsDataSource(roomService)
    }

    @Test
    fun `should get user runtime`() {
//        val userStatsEntity: Single<UserStatsEntity> = Single.just(UserStatsEntity(11))
//        given(roomService.userStatsDao).willReturn(mock())
//        given(roomService.userStatsDao.getUserFullRuntime()).willReturn(userStatsEntity)

        userStatsDataSource.getUserFullRuntime()

        verify(roomService.userStatsDao).getUserFullRuntime()
    }

}
