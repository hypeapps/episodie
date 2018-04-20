package pl.hypeapp.dataproviders.entity.mapper

import org.amshove.kluent.`should equal`
import org.junit.Before
import org.junit.Test
import pl.hypeapp.dataproviders.entity.mapper.watched.WatchedSeasonCountModelMapper
import pl.hypeapp.dataproviders.entity.room.WatchedSeasonCountEntity

class WatchedSeasonCountModelMapperTest {

    private lateinit var watchedSeasonCountModelMapper: WatchedSeasonCountModelMapper

    @Before
    fun setUp() {
        watchedSeasonCountModelMapper = WatchedSeasonCountModelMapper()
    }

    @Test
    fun `should transform entity to model`() {
        val model = watchedSeasonCountModelMapper.transform(fakeEntity)

        model.count `should equal` fakeEntity.count
        model.id `should equal` fakeEntity.seasonId
    }

    private val fakeEntity: WatchedSeasonCountEntity = WatchedSeasonCountEntity(
            seasonId = "tts132123",
            count = 132
    )

}
