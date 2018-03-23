package pl.hypeapp.dataproviders.entity.mapper

import org.junit.Before
import org.junit.Test
import pl.hypeapp.dataproviders.entity.mapper.watched.WatchedEpisodeEntityMapper
import pl.hypeapp.domain.model.tvshow.EpisodeModel
import java.util.*

class WatchedEpisodeEntityMapperTest {

    private lateinit var watchedEpisodeEntityMapper: WatchedEpisodeEntityMapper

    @Before
    fun setUp() {
        watchedEpisodeEntityMapper = WatchedEpisodeEntityMapper()
    }

    @Test
    fun `should transform model to entity`() {
        val watchedEpisodeEntity = watchedEpisodeEntityMapper.transform(fakeModel)

        watchedEpisodeEntity.episodeId `should equal` fakeModel.episodeId
        watchedEpisodeEntity.tvShowId `should equal` fakeModel.tvShowId
        watchedEpisodeEntity.seasonId `should equal` fakeModel.seasonId
        watchedEpisodeEntity.runtime `should equal` fakeModel.runtime
    }

    private val fakeModel = EpisodeModel(episodeId = "tte1321",
            seasonId = "tts123",
            tvShowId = "ttt1213",
            runtime = 21313L,
            name = "Fake",
            summary = "Summary",
            imageMedium = "image",
            episodeNumber = 1,
            seasonNumber = 1,
            premiereDate = Date(),
            watchState = 0)

}
