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
import pl.hypeapp.domain.model.tvshow.TvShowExtendedModel
import pl.hypeapp.domain.repository.AllSeasonsRepository
import pl.hypeapp.domain.usecase.gettvshow.GetTvShowExtendedUseCase
import pl.hypeapp.domain.usecase.watchstate.mapwatched.WatchStateMapper

class AllEpisodesUseCaseTest {

    private lateinit var useCase: GetTvShowExtendedUseCase

    private val repository: AllSeasonsRepository = mock()

    private val threadExecutor: ThreadExecutor = mock()

    private val postExecutionThread: PostExecutionThread = mock()

    private val watchStateMapper: WatchStateMapper = mock()

    companion object {
        val ID = "12231"
        val UPDATE = true
    }

    @Before
    fun setUp() {
        useCase = GetTvShowExtendedUseCase(threadExecutor, postExecutionThread, watchStateMapper, repository)
    }

    @Test
    fun `should get all seasons`() {
        val params: GetTvShowExtendedUseCase.Params = GetTvShowExtendedUseCase.Params.createQuery(ID, UPDATE)
        val tvShowExtendedModel: TvShowExtendedModel = mock()
        val returnedSingle: Single<TvShowExtendedModel> = Single.just(tvShowExtendedModel)
        given(repository.getAllSeasonsAfterPremiereDate(ID, UPDATE)).willReturn(returnedSingle)

        useCase.createSingle(params).blockingGet()

        verify(repository).getAllSeasonsAfterPremiereDate(ID, UPDATE)
        verify(watchStateMapper).map(tvShowExtendedModel)
        verifyNoMoreInteractions(repository)
    }

}
