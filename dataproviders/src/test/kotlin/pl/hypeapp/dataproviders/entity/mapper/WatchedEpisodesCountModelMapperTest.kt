package pl.hypeapp.dataproviders.entity.mapper

import org.amshove.kluent.`should equal`
import org.junit.Before
import org.junit.Test
import pl.hypeapp.dataproviders.entity.room.WatchedEpisodesCountEntity
import pl.hypeapp.domain.model.WatchedEpisodesCountModel

class WatchedEpisodesCountModelMapperTest {

    private lateinit var watchedEpisodesCountModelMapper: WatchedEpisodesCountModelMapper

    @Before
    fun setUp() {
        watchedEpisodesCountModelMapper = WatchedEpisodesCountModelMapper()
    }

    @Test
    fun `should transform entity to model`() {
        val model: WatchedEpisodesCountModel? = watchedEpisodesCountModelMapper.transform(fakeEntity)

        model?.count `should equal` fakeEntity.count
        model?.id `should equal` fakeEntity.tvShowId
    }

    private val fakeEntity: WatchedEpisodesCountEntity = WatchedEpisodesCountEntity(
            tvShowId = "1232",
            count = 123
    )

}
