package pl.hypeapp.domain.usecase.allepisodes

import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import pl.hypeapp.domain.executor.PostExecutionThread
import pl.hypeapp.domain.executor.ThreadExecutor
import pl.hypeapp.domain.model.AllSeasonsModel
import pl.hypeapp.domain.repository.AllSeasonsRepository
import pl.hypeapp.domain.usecase.mapwatched.TvShowWatchStateMapper

class AllEpisodesUseCaseTest {

    private lateinit var useCase: AllEpisodesUseCase

    private val repository: AllSeasonsRepository = mock()

    private val threadExecutor: ThreadExecutor = mock()

    private val postExecutionThread: PostExecutionThread = mock()

    private val watchStateMapper: TvShowWatchStateMapper = mock()

    companion object {
        val ID = "12231"
        val UPDATE = true
    }

    @Before
    fun setUp() {
        useCase = AllEpisodesUseCase(threadExecutor, postExecutionThread, watchStateMapper, repository)
    }

    @Test
    fun `should get all seasons`() {
        val params: AllEpisodesUseCase.Params = AllEpisodesUseCase.Params.createQuery(ID, UPDATE)
        val allSeasonsModel: AllSeasonsModel = mock()
        val returnedSingle: Single<AllSeasonsModel> = Single.just(allSeasonsModel)
        given(repository.getAllSeasons(ID, UPDATE)).willReturn(returnedSingle)

        useCase.createSingle(params).blockingGet()

        verify(repository).getAllSeasons(ID, UPDATE)
        verify(watchStateMapper).map(allSeasonsModel)
        verifyNoMoreInteractions(repository)
    }

}
