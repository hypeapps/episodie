package pl.hypeapp.episodie.ui.features.seasons

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_seasons.recycler_view_fragment_seasons
import kotlinx.android.synthetic.main.fragment_seasons.swipe_refresh_layout_fragment_seasons
import pl.hypeapp.domain.model.AllSeasonsModel
import pl.hypeapp.domain.model.EpisodeModel
import pl.hypeapp.domain.model.SeasonModel
import pl.hypeapp.domain.model.WatchState
import pl.hypeapp.episodie.App
import pl.hypeapp.episodie.R
import pl.hypeapp.episodie.di.components.DaggerFragmentComponent
import pl.hypeapp.episodie.di.components.FragmentComponent
import pl.hypeapp.episodie.extensions.manageWatchStateIcon
import pl.hypeapp.episodie.ui.base.BaseViewModelFragment
import pl.hypeapp.episodie.ui.base.adapter.ViewTypeDelegateAdapter
import pl.hypeapp.episodie.ui.features.seasons.adapter.OnChangeWatchStateListener
import pl.hypeapp.episodie.ui.features.seasons.adapter.SeasonRecyclerAdapter
import pl.hypeapp.episodie.ui.features.tvshowdetails.TvShowDetailsActivity
import pl.hypeapp.episodie.ui.viewmodel.AllSeasonsViewModel
import pl.hypeapp.presentation.seasons.SeasonsPresenter
import pl.hypeapp.presentation.seasons.SeasonsView
import javax.inject.Inject

class SeasonsFragment : BaseViewModelFragment<AllSeasonsViewModel>(), SeasonsView,
        ViewTypeDelegateAdapter.OnRetryListener, SwipeRefreshLayout.OnRefreshListener,
        OnChangeWatchStateListener {

    override fun getLayoutRes(): Int = R.layout.fragment_seasons

    override val viewModelClass: Class<AllSeasonsViewModel> = AllSeasonsViewModel::class.java

    private lateinit var recyclerAdapter: SeasonRecyclerAdapter

    private lateinit var tvShowId: String

    private var onRetry = false

    private lateinit var seasonLayoutState: SeasonsLayoutState

    @Inject lateinit var presenter: SeasonsPresenter

    private val component: FragmentComponent
        get() = DaggerFragmentComponent.builder()
                .appComponent((activity.application as App).component)
                .build()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        component.inject(this)
        tvShowId = arguments.getString(ARGUMENT_TV_SHOW_ID)
        seasonLayoutState = SeasonsLayoutState(view)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onAttachView(this)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            presenter.onViewShown()
        }
    }

    override fun initSwipeToRefresh() = swipe_refresh_layout_fragment_seasons.setOnRefreshListener(this)

    override fun initRecyclerAdapter() {
        recyclerAdapter = SeasonRecyclerAdapter(this)
        recycler_view_fragment_seasons.apply {
            val linearLayout = LinearLayoutManager(context)
            layoutManager = linearLayout
            adapter = recyclerAdapter
        }
    }

    // While view model is null call request otherwise attach retained model
    override fun loadViewModel() {
        viewModel.loadModel({ presenter.requestAllSeasons(tvShowId, false) },
                { populateRecyclerView(viewModel.retainedModel) })
    }

    override fun populateRecyclerView(seasonsModel: AllSeasonsModel?) {
        seasonLayoutState.onShowContent()
        if (swipe_refresh_layout_fragment_seasons.isRefreshing or onRetry) {
            swipe_refresh_layout_fragment_seasons.isRefreshing = false
            onRetry = false
            initRecyclerAdapter()
        }
        seasonsModel?.let { viewModel.clearAndRetainModel(it) }
        recyclerAdapter.addItems(viewModel.seasonsList)
    }

    override fun showError() {
        if (swipe_refresh_layout_fragment_seasons.isRefreshing) {
            swipe_refresh_layout_fragment_seasons.isRefreshing = false
        }
        seasonLayoutState.onError({ onRetry() })
    }

    override fun showLoading() = seasonLayoutState.onLoading()

    override fun startLoadingAnimation() = seasonLayoutState.startLoadingAnimation()

    override fun showEmptySeasonsMessage() = seasonLayoutState.onEmptySeasonMessage()

    override fun onRetry() {
        onRetry = true
        presenter.requestAllSeasons(tvShowId, false)
    }

    override fun onRefresh() = presenter.requestAllSeasons(tvShowId, true)

    override fun onChangeSeasonWatchState(seasonModel: SeasonModel, view: ImageView) {
        smallBangAnimator.bang(view)
        view.manageWatchStateIcon(WatchState.manageWatchState(seasonModel.watchState))
        presenter.changeSeasonWatchState(seasonModel)
    }

    override fun onChangeEpisodeWatchState(episodeModel: EpisodeModel, view: ImageView) {
        smallBangAnimator.bang(view)
        view.manageWatchStateIcon(WatchState.manageWatchState(episodeModel.watchState))
        presenter.changeEpisodeWatchState(episodeModel)
    }

    override fun onChangeWatchStateError() {
        presenter.checkWatchStateIntegrity(viewModel.retainedModel)
        Toast.makeText(context, getString(R.string.all_toast_error_message), Toast.LENGTH_LONG).show()
    }

    override fun onChangedWatchState() {
        presenter.checkWatchStateIntegrity(viewModel.retainedModel)
        (activity as TvShowDetailsActivity).onChangedChildFragmentWatchState()
    }

    override fun observeWatchStateInParentActivity() {
        (activity as TvShowDetailsActivity).watchStateChangedNotifySubject.subscribe {
            presenter.checkWatchStateIntegrity(viewModel.retainedModel)
        }
    }

    override fun updateRecyclerList(seasonsModel: AllSeasonsModel?) {
        seasonsModel?.let { viewModel.clearAndRetainModel(it) }
        recyclerAdapter.updateItems(viewModel.seasonsList)
    }

    companion object {
        val ARGUMENT_TV_SHOW_ID = "TV_SHOW_ID"
        private val bundle: Bundle = Bundle()

        fun newInstance(tvShowId: String?): SeasonsFragment = with(SeasonsFragment()) {
            bundle.putString(ARGUMENT_TV_SHOW_ID, tvShowId)
            this.arguments = bundle
            return this
        }
    }

}
