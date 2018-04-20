package pl.hypeapp.dataproviders.datasource

import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import pl.hypeapp.dataproviders.entity.room.WatchedEpisodeEntity
import pl.hypeapp.dataproviders.entity.room.WatchedEpisodesCountEntity
import pl.hypeapp.dataproviders.entity.room.WatchedSeasonCountEntity
import pl.hypeapp.dataproviders.service.room.RoomService

class WatchedDataSourceTest {

    private lateinit var watchedDataSource: WatchedShowDataSource

    private val roomService: RoomService = mock()

    @Before
    fun setUp() {
        watchedDataSource = WatchedShowDataSource(roomService)
        given(roomService.watchedEpisodeDao).willReturn(mock())
    }

    @Test
    fun `should add tv show to watched`() {
        val watchedEpisodesEntites: List<WatchedEpisodeEntity> = mock()

        watchedDataSource.addTvShow(watchedEpisodesEntites)

        verify(roomService.watchedEpisodeDao).insertWatchedEpisodes(watchedEpisodesEntites)
    }

    @Test
    fun `should add episode to watched`() {
        val watchedEpisode: WatchedEpisodeEntity = mock()

        watchedDataSource.addEpisode(watchedEpisode)

        verify(roomService.watchedEpisodeDao).insertWatchedEpisode(watchedEpisode)
    }

    @Test
    fun `should delete tv show from watched`() {
        val tvShowId = "tt112"

        watchedDataSource.deleteTvShow(tvShowId)

        verify(roomService.watchedEpisodeDao).deleteWatchedTvShow(tvShowId)
    }

    @Test
    fun `should delete season from watched`() {
        val seasonId = "tts121"

        watchedDataSource.deleteSeasonFromWatched(seasonId)

        verify(roomService.watchedEpisodeDao).deleteWatchedSeason(seasonId)
    }

    @Test
    fun `should delete episode from watched`() {
        val episodeId = "tte121"

        watchedDataSource.deleteEpisodeFromWatched(episodeId)

        verify(roomService.watchedEpisodeDao).deleteWatchedEpisode(episodeId)
    }

    @Test
    fun `should get watched episode count by tv show id`() {
        val tvShowId = "tt123"
        val watchedEpisodeCountEntity: WatchedEpisodesCountEntity = mock()
        given(roomService.watchedEpisodeDao.getWatchedEpisodesCountByTvShowId(tvShowId)).willReturn(watchedEpisodeCountEntity)

        watchedDataSource.getWatchedEpisodesCountByTvShowId(tvShowId)

        verify(roomService.watchedEpisodeDao).getWatchedEpisodesCountByTvShowId(tvShowId)
    }

    @Test
    fun `should get watched episodes count by tv show ids`() {
        val tvShowIds = listOf("tt123", "tt1123")
        val watchedEpisodesCountEntites: List<WatchedEpisodesCountEntity> = mock()
        given(roomService.watchedEpisodeDao.getWatchedEpisodesCountByTvShowIds(tvShowIds)).willReturn(watchedEpisodesCountEntites)

        watchedDataSource.getWatchedEpisodesCountByTvShowIds(tvShowIds)

        verify(roomService.watchedEpisodeDao).getWatchedEpisodesCountByTvShowIds(tvShowIds)
    }

    @Test
    fun `should get watched episodes count by seasons ids`() {
        val seasonIds = listOf("tts123", "tts1123")
        val watchedSeasonsCountEntities: List<WatchedSeasonCountEntity> = mock()
        given(roomService.watchedEpisodeDao.getWatchedEpisodesCountBySeasonIds(seasonIds)).willReturn(watchedSeasonsCountEntities)

        watchedDataSource.getWatchedEpisodesCountBySeasonIds(seasonIds)

        verify(roomService.watchedEpisodeDao).getWatchedEpisodesCountBySeasonIds(seasonIds)
    }

    @Test
    fun `should get watched episodes ids by season id`() {
        val seasonId = "tts221"
        val watchedEpisodesIds: List<String> = listOf("tte121", "tte123")
        given(roomService.watchedEpisodeDao.getWatchedEpisodesIdsBySeasonId(seasonId)).willReturn(watchedEpisodesIds)

        watchedDataSource.getWatchedEpisodesIdsBySeasonId(seasonId)

        verify(roomService.watchedEpisodeDao).getWatchedEpisodesIdsBySeasonId(seasonId)
    }

    @Test
    fun `should properly commit transaction`() {
        val episodeId = "tte121"

        watchedDataSource.deleteEpisodeFromWatched(episodeId)

        verify(roomService).beginTransaction()
        verify(roomService).setTransactionSuccessful()
        verify(roomService).endTransaction()
    }

}
