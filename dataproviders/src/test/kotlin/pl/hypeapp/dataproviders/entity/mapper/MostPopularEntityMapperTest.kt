package pl.hypeapp.dataproviders.entity.mapper

import org.amshove.kluent.`should equal to`
import org.amshove.kluent.`should equal`
import org.amshove.kluent.mock

import org.junit.Before
import org.junit.Test
import pl.hypeapp.dataproviders.entity.MostPopularEntity

class MostPopularEntityMapperTest {

    private lateinit var mostPopularEntityMapper: MostPopularEntityMapper

    @Before
    fun setUp() {
        mostPopularEntityMapper = MostPopularEntityMapper()
    }

    @Test
    fun `should transform entity to model`() {
        val entity: MostPopularEntity = createFakeMostPopularEntity()

        val mostPopularModel = mostPopularEntityMapper.transform(entity)

        entity.tvShows `should equal` mostPopularModel.tvShows
        entity.first `should equal` mostPopularModel.pageableRequest.first
        entity.last `should equal` mostPopularModel.pageableRequest.last
        entity.page `should equal` mostPopularModel.pageableRequest.page
        entity.size `should equal` mostPopularModel.pageableRequest.size
        entity.numberOfElements `should equal` mostPopularModel.pageableRequest.numberOfElements
        entity.totalElements `should equal to` mostPopularModel.pageableRequest.totalElements
        entity.totalPages `should equal` mostPopularModel.pageableRequest.totalPages
    }

    private fun createFakeMostPopularEntity(): MostPopularEntity {
        val mostPopularEntity: MostPopularEntity = mock()
        return mostPopularEntity
    }

}
