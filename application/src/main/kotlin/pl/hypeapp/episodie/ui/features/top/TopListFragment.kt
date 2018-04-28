package pl.hypeapp.episodie.ui.features.top

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main_feed.fab_button_main_feed_search
import kotlinx.android.synthetic.main.activity_main_feed.navigation_bottom_layout
import kotlinx.android.synthetic.main.fragment_top_list.*
import pl.hypeapp.domain.model.WatchState
import pl.hypeapp.domain.model.collections.TopListModel
import pl.hypeapp.domain.model.tvshow.TvShowModel
import pl.hypeapp.episodie.App
import pl.hypeapp.episodie.R
import pl.hypeapp.episodie.di.components.DaggerFragmentComponent
import pl.hypeapp.episodie.di.components.FragmentComponent
import pl.hypeapp.episodie.extensions.loadDrawableResource
import pl.hypeapp.episodie.extensions.manageWatchStateIcon
import pl.hypeapp.episodie.extensions.setActionStatusBarPadding
import pl.hypeapp.episodie.navigation.Navigator
import pl.hypeapp.episodie.navigation.STATE_CHANGED
import pl.hypeapp.episodie.ui.base.BaseViewModelFragment
import pl.hypeapp.episodie.ui.base.adapter.InfiniteScrollListener
import pl.hypeapp.episodie.ui.base.adapter.TvShowRecyclerAdapter
import pl.hypeapp.episodie.ui.base.adapter.ViewTypeDelegateAdapter
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
                .appComponent((activity?.application as App).component)
                .build()

    private lateinit var topListRecyclerAdapter: TvShowRecyclerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View? = super.onCreateView(inflater, container, savedInstanceState)
        component.inject(this)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onAttachView(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onDetachView()
    }

    override fun loadBackdrop() {
        image_view_top_list_backdrop.loadDrawableResource(R.drawable.top_list_header)
    }

    // While view model is null call request otherwise attach retained model
    override fun loadViewModel() {
        viewModel.loadModel({ presenter.requestTopList(viewModel.page, false) },
                { topListRecyclerAdapter.addItems(viewModel.tvShowList) })
    }

    override fun onRefresh() = presenter.requestTopList(0, true)

    override fun onRetry() = presenter.requestTopList(viewModel.page, false)

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

    override fun updateRecyclerList(tvShows: List<TvShowModel>) {
        viewModel.updateModel(tvShows)
        topListRecyclerAdapter.updateItems(viewModel.tvShowList)
    }

    override fun showError() = with(swipe_refresh_layout_top_list) {
        topListRecyclerAdapter.addErrorItem()
        if (isRefreshing) {
            isRefreshing = false
        }
    }

    override fun onItemSelected(item: TvShowModel?, vararg views: View) {
        item?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Navigator.startTvShowDetailsWithSharedElements(activity as Activity, it, views)
            } else {
                Navigator.startTvShowDetails(activity as Activity, it)
            }
        }
    }

    override fun onWatchStateChange(tvShow: TvShowModel, diamondIcon: ImageView) {
        smallBangAnimator.bang(diamondIcon)
        diamondIcon.manageWatchStateIcon(WatchState.toggleWatchState(tvShow.watchState))
        presenter.toggleWatchState(tvShow)
    }

    override fun onChangeWatchStateError() {
        Toast.makeText(context, getString(R.string.all_toast_error_message), Toast.LENGTH_LONG).show()
        presenter.checkWatchStateIntegrity(viewModel.tvShowList.map { it.tvShow!! })
    }

    // On activity reenter and on changed watch tv show state needs to update view model
    override fun observeActivityReenter() {
        (activity as MainFeedActivity).onActivityReenterSubject.subscribe({
            if (it == STATE_CHANGED)
                viewModel.tvShowList.let { presenter.checkWatchStateIntegrity(viewModel.tvShowList.map { it.tvShow!! }) }
            presenter.updateUserRuntime()
        })
    }

    override fun showRuntimeNotification(oldUserRuntime: Long, newRuntime: Long) =
            alerter_top_list.show(oldUserRuntime, newRuntime)

    override fun observeDragDrawer() {
        (activity as MainFeedActivity).navigationDrawer.onDrag()?.subscribe({ presenter.onDrawerDrag(it) })
    }

    override fun animateDrawerHamburgerArrow(progress: Float) = image_view_top_list_ic_back_arrow.setProgress(progress)

    override fun initSwipeRefreshLayout() = with(swipe_refresh_layout_top_list) {
        setProgressViewEndTarget(true, 400)
        setOnRefreshListener(this@TopListFragment)
    }

    override fun initRecyclerAdapter() {
        topListRecyclerAdapter = TvShowRecyclerAdapter(resources.getInteger(R.integer.max_items_recycler_top_list), TopListDelegateAdapter(this), this)
        recycler_view_top_list.apply {
            setHasFixedSize(true)
            val linearLayout = LinearLayoutManager(context)
            layoutManager = linearLayout
            addOnScrollListener(OnScrollHideBottomNavigationListener((activity as MainFeedActivity).navigation_bottom_layout))
            addOnScrollListener(OnScrollHideFabButtonListener((activity as MainFeedActivity).fab_button_main_feed_search))
            // When scroll to end of list presenter load next page of most popular
            addOnScrollListener(InfiniteScrollListener({ presenter.requestTopList(++viewModel.page, false) },
                    linearLayout))
            setActionStatusBarPadding(insetPaddingTop = false)
            adapter = topListRecyclerAdapter
        }
    }

}
