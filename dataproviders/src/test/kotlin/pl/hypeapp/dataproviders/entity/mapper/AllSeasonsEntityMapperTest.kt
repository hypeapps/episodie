package pl.hypeapp.dataproviders.entity.mapper

import com.nhaarman.mockito_kotlin.mock
import org.amshove.kluent.`should equal`
import org.junit.Before
import org.junit.Test
import pl.hypeapp.dataproviders.entity.api.AllSeasonsEntity
import pl.hypeapp.domain.model.TvShowExtendedModel

class AllSeasonsEntityMapperTest {

    private lateinit var allSeasonsEntityMapper: AllSeasonsEntityMapper

    private val seasonEntityMapper: SeasonEntityMapper = mock()

    private val allSeasonsEntity: AllSeasonsEntity = mock()

    @Before
    fun setUp() {
        allSeasonsEntityMapper = AllSeasonsEntityMapper(seasonEntityMapper)
    }

    @Test
    fun `should transform entity to model`() {
        val model: TvShowExtendedModel = allSeasonsEntityMapper.transform(allSeasonsEntity)

        model.seasons `should equal` allSeasonsEntity.seasons
    }

}
