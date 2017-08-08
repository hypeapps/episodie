package pl.hypeapp.episodie.ui.features.top

import android.os.Build
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main_feed.fab_button_main_feed_search
import kotlinx.android.synthetic.main.activity_main_feed.navigation_bottom_layout
import kotlinx.android.synthetic.main.fragment_top_list.image_view_top_list_backdrop
import kotlinx.android.synthetic.main.fragment_top_list.image_view_tv_show_details_ic_back_arrow
import kotlinx.android.synthetic.main.fragment_top_list.recycler_view_top_list
import kotlinx.android.synthetic.main.fragment_top_list.swipe_refresh_layout_top_list
import pl.hypeapp.domain.model.TopListModel
import pl.hypeapp.domain.model.TvShowModel
import pl.hypeapp.episodie.App
import pl.hypeapp.episodie.R
import pl.hypeapp.episodie.adapter.InfiniteScrollListener
import pl.hypeapp.episodie.adapter.TvShowRecyclerAdapter
import pl.hypeapp.episodie.adapter.ViewTypeDelegateAdapter
import pl.hypeapp.episodie.di.components.DaggerFragmentComponent
import pl.hypeapp.episodie.di.components.FragmentComponent
import pl.hypeapp.episodie.extensions.loadDrawableResource
import pl.hypeapp.episodie.extensions.setRecyclerViewPadding
import pl.hypeapp.episodie.navigation.Navigator
import pl.hypeapp.episodie.ui.base.BaseViewModelFragment
import pl.hypeapp.episodie.ui.features.mainfeed.MainFeedActivity
import pl.hypeapp.episodie.ui.features.mainfeed.listener.OnScrollHideBottomNavigationListener
import pl.hypeapp.episodie.ui.features.mainfeed.listener.OnScrollHideFabButtonListener
import pl.hypeapp.episodie.ui.features.top.adapter.TopListDelegateAdapter
import pl.hypeapp.episodie.ui.features.top.adapter.TopListOnViewSelectedListener
import pl.hypeapp.episodie.ui.viewmodel.TopListViewModel
import pl.hypeapp.presentation.toplist.TopListPresenter
import pl.hypeapp.presentation.toplist.TopListView
import javax.inject.Inject

class TopListFragment : BaseViewModelFragment<TopListViewModel>(), TopListView, TopListOnViewSelectedListener,
        ViewTypeDelegateAdapter.OnRetryListener, SwipeRefreshLayout.OnRefreshListener {

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
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onAttachView(this)
        image_view_top_list_backdrop.loadDrawableResource(R.drawable.mrrobot_background)
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
        if (swipe_refresh_layout_top_list.isRefreshing) {
            swipe_refresh_layout_top_list.isRefreshing = false
            initRecyclerAdapter()
            topListModel?.let { viewModel.clearAndRetainModel(it) }
        } else {
            topListModel?.let { viewModel.retainModel(it) }
        }
        topListRecyclerAdapter.addItems(viewModel.tvShowList)
    }

    override fun showError() = with(swipe_refresh_layout_top_list) {
        topListRecyclerAdapter.addErrorItem()
        if (isRefreshing) {
            isRefreshing = false
        }
    }

    override fun onItemSelected(item: TvShowModel?, cover: View, title: View) {
        item?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Navigator.startTvShowDetailsWithSharedElement(activity, it, cover, title)
            } else {
                Navigator.startTvShowDetails(activity, it)
            }
        }
    }

    override fun onAddToWatched(tvShowId: String) {
        Log.e("on add to watched", tvShowId)
    }

    override fun onRefresh() = presenter.requestTopList(0, true)

    override fun onRetry() = presenter.requestTopList(viewModel.page, false)

    override fun initSwipeRefreshLayout() = with(swipe_refresh_layout_top_list) {
        setProgressViewEndTarget(true, 400)
        setOnRefreshListener(this@TopListFragment)
    }

    override fun observeDragDrawer() {
        (activity as MainFeedActivity).navigationDrawer.onDrag()?.subscribe({ presenter.onDrawerDrag(it) })
    }

    override fun animateDrawerHamburgerArrow(progress: Float) {
        image_view_tv_show_details_ic_back_arrow.setProgress(progress)
    }

    override fun initRecyclerAdapter() {
        topListRecyclerAdapter = TvShowRecyclerAdapter(resources.getInteger(R.integer.max_items_recycler_top_list), TopListDelegateAdapter(this), this)
        recycler_view_top_list.apply {
            setHasFixedSize(true)
            val linearLayout = LinearLayoutManager(context)
            layoutManager = linearLayout
            addOnScrollListener(OnScrollHideBottomNavigationListener((activity as MainFeedActivity).navigation_bottom_layout))
            addOnScrollListener(OnScrollHideFabButtonListener((activity as MainFeedActivity).fab_button_main_feed_search))
            // When list scroll to end presenter loadOrRetainModel next page of most popular
            addOnScrollListener(InfiniteScrollListener({ presenter.requestTopList(++viewModel.page, false) },
                    linearLayout))
            setRecyclerViewPadding(insetPaddingTop = false)
            adapter = topListRecyclerAdapter
        }
    }

}
