package pl.hypeapp.dataproviders.entity.mapper

import com.nhaarman.mockito_kotlin.mock
import org.amshove.kluent.`should equal`
import org.junit.Before
import org.junit.Test
import pl.hypeapp.dataproviders.entity.EpisodeEntity

class EpisodeEntityMapperTest {

    private lateinit var mapper: EpisodeEntityMapper

    private val entity: EpisodeEntity = mock()

    @Before
    fun setUp() {
        mapper = EpisodeEntityMapper()
    }

    @Test
    fun `should transform entity to model`() {
        val model = mapper.transform(entity)

        model?.premiereDate `should equal` entity.premiereDate
        model?.episodeId `should equal` entity.episodeId
        model?.episodeNumber `should equal` entity.episodeNumber
        model?.name `should equal` entity.name
        model?.seasonNumber `should equal` entity.seasonNumber
        model?.runtime `should equal` entity.runtime
        model?.summary `should equal` entity.summary
        model?.imageMedium `should equal` entity.imageMedium
    }

}
