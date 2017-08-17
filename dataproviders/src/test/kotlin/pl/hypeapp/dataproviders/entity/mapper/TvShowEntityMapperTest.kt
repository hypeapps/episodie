package pl.hypeapp.dataproviders.entity.mapper

import org.amshove.kluent.`should equal`
import org.amshove.kluent.mock
import org.junit.Before
import org.junit.Test
import pl.hypeapp.dataproviders.entity.TvShowEntity

class TvShowEntityMapperTest {

    private lateinit var tvShowEntityMapper: TvShowEntityMapper

    @Before
    fun setUp() {
        tvShowEntityMapper = TvShowEntityMapper()
    }

    @Test
    fun `should transform entity to model`() {
        val entity: TvShowEntity = createFakeTvShowEntity()

        val tvShowModel = tvShowEntityMapper.transform(entity)

        entity.name `should equal` tvShowModel.name
        entity.id `should equal` tvShowModel.id
        entity.imdbId `should equal` tvShowModel.imdbId
        entity.episodeRuntime `should equal` tvShowModel.episodeRuntime
        entity.fullRuntime `should equal` tvShowModel.fullRuntime
        entity.imageMedium `should equal` tvShowModel.imageMedium
        entity.imageOriginal `should equal` tvShowModel.imageOriginal
        entity.premiered `should equal` tvShowModel.premiered
        entity.summary `should equal` tvShowModel.summary
        entity.status `should equal` tvShowModel.status
        entity.network `should equal` tvShowModel.network
        entity.genre `should equal` tvShowModel.genre
        entity.officialSite `should equal` tvShowModel.officialSite
    }

    private fun createFakeTvShowEntity(): TvShowEntity {
        val tvShowEntity: TvShowEntity = mock()
        return tvShowEntity
    }

}
