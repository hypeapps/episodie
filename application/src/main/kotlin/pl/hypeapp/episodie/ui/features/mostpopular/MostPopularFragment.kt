package pl.hypeapp.episodie.ui.features.mostpopular

import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import pl.hypeapp.domain.model.MostPopularModel
import pl.hypeapp.episodie.App
import pl.hypeapp.episodie.R
import pl.hypeapp.episodie.adapter.InfiniteScrollListener
import pl.hypeapp.episodie.adapter.ViewType
import pl.hypeapp.episodie.adapter.ViewTypeDelegateAdapter
import pl.hypeapp.episodie.di.components.DaggerFragmentComponent
import pl.hypeapp.episodie.di.components.FragmentComponent
import pl.hypeapp.episodie.extensions.getActionBarSize
import pl.hypeapp.episodie.extensions.getNavigationBarSize
import pl.hypeapp.episodie.extensions.getStatusBarHeight
import pl.hypeapp.episodie.ui.base.BaseFragment
import pl.hypeapp.episodie.ui.features.mainfeed.MainFeedActivity
import pl.hypeapp.episodie.ui.features.mainfeed.listener.OnScrollHideBottomNavigationListener
import pl.hypeapp.episodie.ui.features.mainfeed.listener.OnScrollHideFabButtonListener
import pl.hypeapp.episodie.ui.features.mostpopular.adapter.MostPopularRecyclerAdapter
import pl.hypeapp.episodie.ui.viewmodel.MostPopularViewModel
import pl.hypeapp.presentation.mostpopular.MostPopularPresenter
import pl.hypeapp.presentation.mostpopular.MostPopularView
import javax.inject.Inject

class MostPopularFragment : BaseFragment<MostPopularViewModel>(), MostPopularView, ViewTypeDelegateAdapter.OnViewSelectedListener,
        ViewTypeDelegateAdapter.OnRetryListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recycler_view_most_popular)
    lateinit var recyclerView: RecyclerView

    @BindView(R.id.swipe_refresh_layout_most_popular)
    lateinit var swipeRefresh: SwipeRefreshLayout

    @Inject
    lateinit var presenter: MostPopularPresenter

    override fun getLayoutRes(): Int = R.layout.fragment_most_popular

    override val viewModelClass: Class<MostPopularViewModel> = MostPopularViewModel::class.java

    private lateinit var mostPopularRecyclerAdapter: MostPopularRecyclerAdapter

    private val component: FragmentComponent
        get() = DaggerFragmentComponent.builder()
                .appComponent((activity.application as App).component)
                .build()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View? = super.onCreateView(inflater, container, savedInstanceState)
        initRecyclerAdapter()
        initSwipeRefreshLayout()
        component.inject(this)
        presenter.onAttachView(this)
        return view
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
        // If pull to refresh we need to clear view model and init recycler adapter
        if (swipeRefresh.isRefreshing) {
            swipeRefresh.isRefreshing = false
            initRecyclerAdapter()
            mostPopularModel?.let { viewModel.clearAndRetainModel(it) }
        } else {
            mostPopularModel?.let { viewModel.retainModel(it) }
        }
        mostPopularRecyclerAdapter.addItems(viewModel.tvShowList)
    }

    override fun showError() {
        mostPopularRecyclerAdapter.addErrorItem()
        if (swipeRefresh.isRefreshing) {
            swipeRefresh.isRefreshing = false
        }
    }

    override fun onRefresh() {
        presenter.requestMostPopular(0, true)
    }

    override fun onRetry() {
        presenter.requestMostPopular(viewModel.page, false)
    }

    override fun onItemSelected() {
        TODO("not implemented")
    }

    private fun initSwipeRefreshLayout() = with(swipeRefresh) {
        setProgressViewEndTarget(true, 400)
        setOnRefreshListener(this@MostPopularFragment)
    }

    private fun initRecyclerAdapter() {
        mostPopularRecyclerAdapter = MostPopularRecyclerAdapter(resources.getInteger(R.integer.max_items_recycler_most_popular), this, this)
        recyclerView.apply {
            setHasFixedSize(true)
            val gridLayout = GridLayoutManager(context, resources.getInteger(R.integer.span_count_recycler_most_popular))
            gridLayout.spanSizeLookup = spanSizeLookup()
            layoutManager = gridLayout
            addOnScrollListener(OnScrollHideBottomNavigationListener((activity as MainFeedActivity).bottomNavigationLayout))
            addOnScrollListener(OnScrollHideFabButtonListener((activity as MainFeedActivity).fabButton))
            // When list scroll to end presenter load next page of most popular
            addOnScrollListener(InfiniteScrollListener({ presenter.requestMostPopular(++viewModel.page, false) },
                    gridLayout))
            // If orientation is landscape add navigation bar size to padding end
            val navigationBarPadding: Int = if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
                context.getNavigationBarSize().x
            else 0
            setPadding(paddingStart, (resources.getStatusBarHeight() + context.getActionBarSize()), navigationBarPadding,
                    paddingBottom)
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
