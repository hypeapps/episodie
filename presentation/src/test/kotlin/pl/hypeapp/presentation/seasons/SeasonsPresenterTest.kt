package pl.hypeapp.presentation.seasons

class SeasonsPresenterTest {

//    private lateinit var presenter: SeasonsPresenter
//
//    private val useCase: AllEpisodesUseCase = mock()
//
//    private val view: SeasonsView = mock()
//
//    private val manageEpisodeWatchStateUseCase: ManageEpisodeWatchStateUseCase = mock()
//
//    private val manageSeasonWatchStateUseCase: ManageSeasonsWatchStateUseCase = mock()
//
//    private val seasonWatchStateIntegrityUseCase: SeasonWatchStateIntegrityUseCase = mock()
//
//    private val tvShowId = "1212"
//
//    private val update = false
//
//    @Before
//    fun setUp() {
//        presenter = SeasonsPresenter(useCase, manageEpisodeWatchStateUseCase, manageSeasonWatchStateUseCase,
//                seasonWatchStateIntegrityUseCase)
//    }
//
//    @Test
//    fun `should init pager adapter`() {
//        presenter.view = view
//
//        presenter.onViewShown()
//
//        verify(view).initRecyclerAdapter()
//    }
//
//    @Test
//    fun `should observer watch state in parent activity`() {
//        presenter.view = view
//
//        presenter.onViewShown()
//
//        verify(view).observeWatchStateInParentActivity()
//    }
//
//    @Test
//    fun `should init swipe to refresh`() {
//        presenter.view = view
//
//        presenter.onViewShown()
//
//        verify(view).initSwipeToRefresh()
//    }
//
//    @Test
//    fun `should show loading on attach view`() {
//        presenter.onAttachView(view)
//
//        verify(view).showLoading()
//    }
//
//    @Test
//    fun `should dispose use case`() {
//        presenter.onDetachView()
//        verify(useCase).dispose()
//    }
//
//    @Test
//    fun `should load view model on view shown`() {
//        presenter.view = view
//
//        presenter.onViewShown()
//
//        verify(view).loadViewModel()
//        assertEquals(presenter.isViewShown, true)
//    }
//
//    @Test
//    fun `should no load view model on view already shown`() {
//        presenter.view = view
//        presenter.isViewShown = true
//
//        presenter.onViewShown()
//
//        verifyZeroInteractions(view)
//        assertEquals(presenter.isViewShown, true)
//    }
//
//    @Test
//    fun `should request all seasons and show loading`() {
//        presenter.view = view
//
//        presenter.requestAllSeasons(tvShowId, update)
//
//        verify(view).showLoading()
//        verify(useCase).execute(any(), any())
//    }
//
//    @Test
//    fun `should check watch state integrity`() {
//        presenter.isViewShown = true
//        val tvShowExtendedModel: TvShowExtendedModel = mock()
//
//        presenter.checkWatchStateIntegrity(tvShowExtendedModel)
//
//        verify(seasonWatchStateIntegrityUseCase).execute(any(), any())
//    }
//
//    @Test
//    fun `should pass model and populate recycler`() {
//        presenter.view = view
//        val model: TvShowExtendedModel = fakeModel
//
//        `when`(useCase.execute(any(), any())).thenAnswer {
//            (it.arguments[0] as SeasonsPresenter.SeasonsObserver).onSuccess(model)
//        }
//
//        presenter.requestAllSeasons(tvShowId, update)
//
//        verify(view).populateRecyclerView(model)
//        verify(view, times(0)).showEmptySeasonsMessage()
//    }
//
//    @Test
//    fun `should show empty seasons message`() {
//        presenter.view = view
//        val model: TvShowExtendedModel = mock()
//
//        `when`(useCase.execute(any(), any())).thenAnswer {
//            (it.arguments[0] as SeasonsPresenter.SeasonsObserver).onSuccess(model)
//        }
//
//        presenter.requestAllSeasons(tvShowId, update)
//
//        verify(view).showEmptySeasonsMessage()
//        verify(view, times(0)).populateRecyclerView(model)
//    }
//
//    @Test
//    fun `should show error`() {
//        presenter.view = view
//        val throwable: Throwable = mock()
//
//        `when`(useCase.execute(any(), any())).thenAnswer {
//            (it.arguments[0] as SeasonsPresenter.SeasonsObserver).onError(throwable)
//        }
//
//        presenter.requestAllSeasons(tvShowId, update)
//
//        verify(view).showError()
//    }
//
//    var seasonModel: SeasonModel = mock()
//
//    var fakeModel: TvShowExtendedModel = TvShowExtendedModel()

}
