package pl.hypeapp.episodie.ui.features.top

import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import butterknife.BindView
import kotlinx.android.synthetic.main.activity_main_feed.fab_search
import kotlinx.android.synthetic.main.activity_main_feed.navigation_bottom_layout
import pl.hypeapp.domain.model.TopListModel
import pl.hypeapp.episodie.App
import pl.hypeapp.episodie.R
import pl.hypeapp.episodie.adapter.InfiniteScrollListener
import pl.hypeapp.episodie.adapter.TvShowRecyclerAdapter
import pl.hypeapp.episodie.adapter.ViewTypeDelegateAdapter
import pl.hypeapp.episodie.di.components.DaggerFragmentComponent
import pl.hypeapp.episodie.di.components.FragmentComponent
import pl.hypeapp.episodie.extensions.getNavigationBarSize
import pl.hypeapp.episodie.extensions.loadDrawableResource
import pl.hypeapp.episodie.ui.base.BaseFragment
import pl.hypeapp.episodie.ui.features.mainfeed.MainFeedActivity
import pl.hypeapp.episodie.ui.features.mainfeed.listener.OnScrollHideBottomNavigationListener
import pl.hypeapp.episodie.ui.features.mainfeed.listener.OnScrollHideFabButtonListener
import pl.hypeapp.episodie.ui.features.navigationdrawer.DrawerHamburgerArrow
import pl.hypeapp.episodie.ui.features.top.adapter.TopListDelegateAdapter
import pl.hypeapp.episodie.ui.features.top.adapter.TopListOnViewSelectedListener
import pl.hypeapp.episodie.ui.viewmodel.TopListViewModel
import pl.hypeapp.presentation.toplist.TopListPresenter
import pl.hypeapp.presentation.toplist.TopListView
import javax.inject.Inject

class TopListFragment : BaseFragment<TopListViewModel>(), TopListView, TopListOnViewSelectedListener,
        ViewTypeDelegateAdapter.OnRetryListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recycler_view_top_list)
    lateinit var recyclerView: RecyclerView

    @BindView(R.id.swipe_refresh_layout_top_list)
    lateinit var swipeRefresh: SwipeRefreshLayout

    @BindView(R.id.drawer_hamburger_item_top_list)
    lateinit var drawerHamburgerArrow: DrawerHamburgerArrow

    @BindView(R.id.image_view_top_list_backdrop)
    lateinit var appBarBackdrop: ImageView

    @Inject
    lateinit var presenter: TopListPresenter

    override val viewModelClass: Class<TopListViewModel> = TopListViewModel::class.java

    override fun getLayoutRes(): Int = R.layout.fragment_top_list

    private val component: FragmentComponent
        get() = DaggerFragmentComponent.builder()
                .appComponent((activity.application as App).component)
                .build()

    private lateinit var topListRecyclerAdapter: TvShowRecyclerAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View? = super.onCreateView(inflater, container, savedInstanceState)
        component.inject(this)
        initRecyclerAdapter()
        presenter.onAttachView(this)
        initSwipeRefreshLayout()
        onDragAnimateDrawerHamburgerArrow()
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appBarBackdrop.loadDrawableResource(R.drawable.mrrobot_background)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onDetachView()
    }

    // While view model is null call request otherwise attach retained model
    override fun loadViewModel() {
        viewModel.loadModel({ presenter.requestTopList(viewModel.page, false) },
                { topListRecyclerAdapter.addItems(viewModel.tvShowList) })
    }

    override fun populateRecyclerList(topListModel: TopListModel?) {
        // If pull to refresh we need to clear view model and re init recycler adapter
        if (swipeRefresh.isRefreshing) {
            swipeRefresh.isRefreshing = false
            initRecyclerAdapter()
            topListModel?.let { viewModel.clearAndRetainModel(it) }
        } else {
            topListModel?.let { viewModel.retainModel(it) }
        }
        topListRecyclerAdapter.addItems(viewModel.tvShowList)
    }

    override fun showError() {
        topListRecyclerAdapter.addErrorItem()
        if (swipeRefresh.isRefreshing) {
            swipeRefresh.isRefreshing = false
        }
    }

    override fun onItemSelected() {
        Log.e("on item", "view selected")
    }

    override fun onAddToWatched(tvShowId: String) {
        Log.e("on add to watched", tvShowId)
    }

    override fun onRefresh() = presenter.requestTopList(0, true)

    override fun onRetry() = presenter.requestTopList(viewModel.page, false)

    private fun initSwipeRefreshLayout() = with(swipeRefresh) {
        setProgressViewEndTarget(true, 400)
        setOnRefreshListener(this@TopListFragment)
    }

    private fun onDragAnimateDrawerHamburgerArrow() {
        (activity as MainFeedActivity).navigationDrawer.onDrag()?.subscribe({ drawerHamburgerArrow.setProgress(it) })
    }

    private fun initRecyclerAdapter() {
        topListRecyclerAdapter = TvShowRecyclerAdapter(resources.getInteger(R.integer.max_items_recycler_top_list), TopListDelegateAdapter(this), this)
        recyclerView.apply {
            setHasFixedSize(true)
            val linearLayout = LinearLayoutManager(context)
            layoutManager = linearLayout
            addOnScrollListener(OnScrollHideBottomNavigationListener((activity as MainFeedActivity).navigation_bottom_layout))
            addOnScrollListener(OnScrollHideFabButtonListener((activity as MainFeedActivity).fab_search))
            // When list scroll to end presenter load next page of most popular
            addOnScrollListener(InfiniteScrollListener({ presenter.requestTopList(++viewModel.page, false) },
                    linearLayout))
            // If orientation is landscape add navigation bar size to padding end
            val navigationBarPadding: Int = if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
                context.getNavigationBarSize().x else 0
            setPadding(paddingStart, paddingTop, navigationBarPadding, paddingBottom)
            adapter = topListRecyclerAdapter
        }
    }

}
