package pl.hypeapp.episodie.ui.features.mostpopular

import android.os.Build
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main_feed.fab_button_main_feed_search
import kotlinx.android.synthetic.main.activity_main_feed.navigation_bottom_layout
import kotlinx.android.synthetic.main.fragment_most_popular.recycler_view_most_popular
import kotlinx.android.synthetic.main.fragment_most_popular.swipe_refresh_layout_most_popular
import pl.hypeapp.domain.model.MostPopularModel
import pl.hypeapp.domain.model.TvShowModel
import pl.hypeapp.episodie.App
import pl.hypeapp.episodie.R
import pl.hypeapp.episodie.di.components.DaggerFragmentComponent
import pl.hypeapp.episodie.di.components.FragmentComponent
import pl.hypeapp.episodie.extensions.setRecyclerViewPadding
import pl.hypeapp.episodie.navigation.Navigator
import pl.hypeapp.episodie.ui.base.BaseViewModelFragment
import pl.hypeapp.episodie.ui.base.adapter.InfiniteScrollListener
import pl.hypeapp.episodie.ui.base.adapter.TvShowRecyclerAdapter
import pl.hypeapp.episodie.ui.base.adapter.ViewType
import pl.hypeapp.episodie.ui.base.adapter.ViewTypeDelegateAdapter
import pl.hypeapp.episodie.ui.features.mainfeed.MainFeedActivity
import pl.hypeapp.episodie.ui.features.mainfeed.listener.OnScrollHideBottomNavigationListener
import pl.hypeapp.episodie.ui.features.mainfeed.listener.OnScrollHideFabButtonListener
import pl.hypeapp.episodie.ui.features.mostpopular.adapter.MostPopularDelegateAdapter
import pl.hypeapp.episodie.ui.viewmodel.MostPopularViewModel
import pl.hypeapp.presentation.mostpopular.MostPopularPresenter
import pl.hypeapp.presentation.mostpopular.MostPopularView
import javax.inject.Inject


class MostPopularFragment : BaseViewModelFragment<MostPopularViewModel>(), MostPopularView, ViewTypeDelegateAdapter.OnViewSelectedListener,
        ViewTypeDelegateAdapter.OnRetryListener, SwipeRefreshLayout.OnRefreshListener {

    override fun getLayoutRes(): Int = R.layout.fragment_most_popular

    override val viewModelClass: Class<MostPopularViewModel> = MostPopularViewModel::class.java

    private lateinit var mostPopularRecyclerAdapter: TvShowRecyclerAdapter

    @Inject
    lateinit var presenter: MostPopularPresenter

    private val component: FragmentComponent
        get() = DaggerFragmentComponent.builder()
                .appComponent((activity.application as App).component)
                .build()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View? = super.onCreateView(inflater, container, savedInstanceState)
        component.inject(this)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onAttachView(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onDetachView()
    }

    // While view model is null call request otherwise attach retained model
    override fun loadViewModel() {
        viewModel.loadModel({ presenter.requestMostPopular(viewModel.page, false) },
                { mostPopularRecyclerAdapter.addItems(viewModel.tvShowList) })
    }

    override fun populateRecyclerList(mostPopularModel: MostPopularModel?) {
        // If pull to refresh we need to clear view model and re init recycler adapter
        if (swipe_refresh_layout_most_popular.isRefreshing) {
            swipe_refresh_layout_most_popular.isRefreshing = false
            initRecyclerAdapter()
            mostPopularModel?.let { viewModel.clearAndRetainModel(it) }
        } else {
            mostPopularModel?.let { viewModel.retainModel(it) }
        }
        mostPopularRecyclerAdapter.addItems(viewModel.tvShowList)
    }

    override fun showError() = with(swipe_refresh_layout_most_popular) {
        mostPopularRecyclerAdapter.addErrorItem()
        if (isRefreshing) {
            isRefreshing = false
        }
    }

    override fun onRefresh() = presenter.onRefresh()

    override fun onRetry() = presenter.requestMostPopular(viewModel.page, false)

    override fun onItemSelected(item: TvShowModel?, vararg views: View) {
        item?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Navigator.startTvShowDetailsWithSharedElement(activity, it, views)
            } else {
                Navigator.startTvShowDetails(activity, it)
            }
        }
    }

    override fun initSwipeRefreshLayout() = with(swipe_refresh_layout_most_popular) {
        setProgressViewEndTarget(true, 400)
        setOnRefreshListener(this@MostPopularFragment)
    }

    override fun initRecyclerAdapter() {
        mostPopularRecyclerAdapter = TvShowRecyclerAdapter(resources.getInteger(R.integer.max_items_recycler_most_popular), MostPopularDelegateAdapter(this), this)
        recycler_view_most_popular.apply {
            setHasFixedSize(true)
            val gridLayout = GridLayoutManager(context, resources.getInteger(R.integer.span_count_recycler_most_popular))
            gridLayout.spanSizeLookup = spanSizeLookup()
            layoutManager = gridLayout
            addOnScrollListener(OnScrollHideBottomNavigationListener((activity as MainFeedActivity).navigation_bottom_layout))
            addOnScrollListener(OnScrollHideFabButtonListener((activity as MainFeedActivity).fab_button_main_feed_search))
            // When list scroll to end presenter loadOrRetainModel next page of most popular
            addOnScrollListener(InfiniteScrollListener({ presenter.requestMostPopular(++viewModel.page, false) },
                    gridLayout))
            setRecyclerViewPadding(insetPaddingTop = true)
            adapter = mostPopularRecyclerAdapter
        }
    }

    private fun spanSizeLookup(): GridLayoutManager.SpanSizeLookup {
        return object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                when (mostPopularRecyclerAdapter.getItemViewType(position)) {
                    ViewType.ITEM -> return resources.getInteger(R.integer.span_size_recycler_most_popular_item)
                    ViewType.LOADING -> return resources.getInteger(R.integer.span_size_recycler_most_popular_loading)
                    ViewType.ERROR -> return resources.getInteger(R.integer.span_size_recycler_most_popular_error)
                    else -> throw IllegalStateException("Invalid view type")
                }
            }
        }
    }

}
