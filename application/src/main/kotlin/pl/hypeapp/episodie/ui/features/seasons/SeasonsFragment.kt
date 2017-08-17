package pl.hypeapp.episodie.ui.features.seasons

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.OnClick
import kotlinx.android.synthetic.main.fragment_seasons.*
import pl.hypeapp.domain.model.AllSeasonsModel
import pl.hypeapp.episodie.App
import pl.hypeapp.episodie.R
import pl.hypeapp.episodie.di.components.DaggerFragmentComponent
import pl.hypeapp.episodie.di.components.FragmentComponent
import pl.hypeapp.episodie.ui.base.BaseViewModelFragment
import pl.hypeapp.episodie.ui.base.adapter.ViewTypeDelegateAdapter
import pl.hypeapp.episodie.ui.features.seasons.adapter.SeasonRecyclerAdapter
import pl.hypeapp.episodie.ui.viewmodel.AllSeasonsViewModel
import pl.hypeapp.presentation.seasons.SeasonsPresenter
import pl.hypeapp.presentation.seasons.SeasonsView
import javax.inject.Inject

class SeasonsFragment : BaseViewModelFragment<AllSeasonsViewModel>(), SeasonsView,
        ViewTypeDelegateAdapter.OnRetryListener, SwipeRefreshLayout.OnRefreshListener {

    override fun getLayoutRes(): Int = R.layout.fragment_seasons

    override val viewModelClass: Class<AllSeasonsViewModel> = AllSeasonsViewModel::class.java

    private lateinit var recyclerAdapter: SeasonRecyclerAdapter

    private lateinit var tvShowId: String

    private var onRetry = false

    @Inject
    lateinit var presenter: SeasonsPresenter

    private val component: FragmentComponent
        get() = DaggerFragmentComponent.builder()
                .appComponent((activity.application as App).component)
                .build()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        component.inject(this)
        tvShowId = arguments.getString(ARGUMENT_TV_SHOW_ID)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.onAttachView(this)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            presenter.onViewShown()
        }
    }

    override fun initSwipeToRefresh() {
        swipe_refresh_layout_fragment_seasons.setOnRefreshListener(this)
    }

    override fun initRecyclerAdapter() {
        recyclerAdapter = SeasonRecyclerAdapter()
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
        text_view_fragment_episodes_empty_seasons.visibility = View.GONE
        view_error_fragment_seasons.visibility = View.GONE
        view_loading_fragment_seasons.visibility = View.GONE
        swipe_refresh_layout_fragment_seasons.visibility = View.VISIBLE
        // If pull to refresh re init recycler adapter
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
        swipe_refresh_layout_fragment_seasons.visibility = View.GONE
        text_view_fragment_episodes_empty_seasons.visibility = View.GONE
        view_loading_fragment_seasons.visibility = View.GONE
        view_error_fragment_seasons.visibility = View.VISIBLE
    }

    override fun showLoading() {
        swipe_refresh_layout_fragment_seasons.visibility = View.GONE
        text_view_fragment_episodes_empty_seasons.visibility = View.GONE
        view_error_fragment_seasons.visibility = View.GONE
        view_loading_fragment_seasons.visibility = View.VISIBLE
    }

    override fun showEmptySeasonsMessage() {
        swipe_refresh_layout_fragment_seasons.visibility = View.GONE
        view_error_fragment_seasons.visibility = View.GONE
        view_loading_fragment_seasons.visibility = View.GONE
        text_view_fragment_episodes_empty_seasons.visibility = View.VISIBLE
    }

    @OnClick(R.id.button_item_error_retry)
    override fun onRetry() {
        onRetry = true
        presenter.requestAllSeasons(tvShowId, false)
    }

    override fun onRefresh() {
        presenter.requestAllSeasons(tvShowId, true)
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
