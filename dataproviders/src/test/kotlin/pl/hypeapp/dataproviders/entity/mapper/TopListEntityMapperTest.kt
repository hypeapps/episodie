package pl.hypeapp.dataproviders.entity.mapper

import org.amshove.kluent.`should equal to`
import org.amshove.kluent.`should equal`
import org.amshove.kluent.mock
import org.junit.Before
import org.junit.Test
import pl.hypeapp.dataproviders.entity.TopListEntity

class TopListEntityMapperTest {

    private lateinit var topListEntityMapper: TopListEntityMapper

    @Before
    fun setUp() {
        topListEntityMapper = TopListEntityMapper()
    }

    @Test
    fun `should transform entity to model`() {
        val entity: TopListEntity = createFakeTopListEntity()

        val topListModel = topListEntityMapper.transform(entity)

        entity.tvShows `should equal` topListModel.tvShows
        entity.first `should equal` topListModel.pageableRequest.first
        entity.last `should equal` topListModel.pageableRequest.last
        entity.page `should equal` topListModel.pageableRequest.page
        entity.size `should equal` topListModel.pageableRequest.size
        entity.numberOfElements `should equal` topListModel.pageableRequest.numberOfElements
        entity.totalElements `should equal to` topListModel.pageableRequest.totalElements
        entity.totalPages `should equal` topListModel.pageableRequest.totalPages
    }

    private fun createFakeTopListEntity(): TopListEntity {
        val topListEntity: TopListEntity = mock()
        return topListEntity
    }

}
