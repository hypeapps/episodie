package pl.hypeapp.episodie.ui.features.premieres

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main_feed.*
import kotlinx.android.synthetic.main.fragment_premieres.*
import pl.hypeapp.domain.model.PremiereDateModel
import pl.hypeapp.domain.model.PremiereDatesModel
import pl.hypeapp.domain.model.PremiereReminderModel
import pl.hypeapp.domain.model.TvShowModel
import pl.hypeapp.episodie.App
import pl.hypeapp.episodie.R
import pl.hypeapp.episodie.di.components.DaggerFragmentComponent
import pl.hypeapp.episodie.di.components.FragmentComponent
import pl.hypeapp.episodie.extensions.getNavigationBarSize
import pl.hypeapp.episodie.extensions.isLandscapeOrientation
import pl.hypeapp.episodie.extensions.isNavigationBarLandscape
import pl.hypeapp.episodie.extensions.loadDrawableResource
import pl.hypeapp.episodie.job.premierereminder.PremiereReminderJob
import pl.hypeapp.episodie.navigation.Navigator
import pl.hypeapp.episodie.ui.base.BaseViewModelFragment
import pl.hypeapp.episodie.ui.base.adapter.InfiniteScrollListener
import pl.hypeapp.episodie.ui.base.adapter.ViewTypeDelegateAdapter
import pl.hypeapp.episodie.ui.features.mainfeed.MainFeedActivity
import pl.hypeapp.episodie.ui.features.mainfeed.listener.OnScrollHideBottomNavigationListener
import pl.hypeapp.episodie.ui.features.mainfeed.listener.OnScrollHideFabButtonListener
import pl.hypeapp.episodie.ui.features.premieres.adapter.OnPremiereViewSelectedListener
import pl.hypeapp.episodie.ui.features.premieres.adapter.PremieresRecyclerAdapter
import pl.hypeapp.episodie.ui.viewmodel.premiere.PremieresViewModel
import pl.hypeapp.presentation.premieres.PremieresPresenter
import pl.hypeapp.presentation.premieres.PremieresView
import java.util.*
import javax.inject.Inject

class PremieresFragment : BaseViewModelFragment<PremieresViewModel>(), PremieresView, OnPremiereViewSelectedListener,
        ViewTypeDelegateAdapter.OnRetryListener, SwipeRefreshLayout.OnRefreshListener {

    override fun getLayoutRes(): Int = R.layout.fragment_premieres

    override val viewModelClass: Class<PremieresViewModel> = PremieresViewModel::class.java

    lateinit var recyclerAdapter: PremieresRecyclerAdapter

    @Inject lateinit var presenter: PremieresPresenter

    private lateinit var transitionView: View

    private val component: FragmentComponent
        get() = DaggerFragmentComponent.builder()
                .appComponent((activity?.application as App).component)
                .build()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View? = super.onCreateView(inflater, container, savedInstanceState)
        component.inject(this)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onAttachView(this)
        if (activity!!.isLandscapeOrientation() and !activity!!.isNavigationBarLandscape()) {
            setLandscapeNavBarPaddingEnd(recycler_view_premieres)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onDetachView()
    }

    override fun loadBackdrop() {
        image_view_premieres_backdrop.loadDrawableResource(R.drawable.bb)
    }

    // While view model is null call request otherwise attach retained model
    override fun loadViewModel() {
        viewModel.loadModel({ presenter.requestPremiereDates(viewModel.page, false) },
                { recyclerAdapter.addItems(viewModel.premiereList, viewModel.isLast) })
    }

    override fun onRefresh() = presenter.requestPremiereDates(0, true)

    override fun onRetry() = presenter.requestPremiereDates(viewModel.page, false)

    override fun onNotificationSchedule(model: PremiereDateModel) {
        model.notificationScheduled = true
        val diffTime = Calendar.getInstance()
        diffTime.time = model.premiereDateFormatted()
        diffTime.add(Calendar.HOUR_OF_DAY, 12)
        val exactTime = diffTime.time.time - Calendar.getInstance().timeInMillis
        val reminder = PremiereReminderModel(
                tvShowId = model.id,
                jobId = PremiereReminderJob.scheduleJob(exactTime, model.name!!),
                timestamp = exactTime)
        presenter.onNotificationSchedule(reminder)
    }

    override fun onNotificationDismiss(model: PremiereDateModel) {
        model.notificationScheduled = false
        presenter.onNotificationDismiss(model.id!!)
    }

    override fun cancelNotification(jobId: Int) = PremiereReminderJob.cancelJob(jobId)

    override fun onPremiereItemClick(model: PremiereDateModel, view: View) {
        this.transitionView = view
        presenter.onPremiereItemClick(model)
    }

    override fun observeDragDrawer() {
        (activity as MainFeedActivity).navigationDrawer.onDrag()?.subscribe({ presenter.onDrawerDrag(it) })
    }

    override fun animateDrawerHamburgerArrow(progress: Float) = image_view_premieres_ic_back_arrow.setProgress(progress)

    override fun showErrorToast() {
        Toast.makeText(context, getString(R.string.all_toast_error_message), Toast.LENGTH_LONG).show()
    }

    override fun showError() = with(swipe_refresh_layout_premieres) {
        recyclerAdapter.addErrorItem()
        if (isRefreshing) {
            isRefreshing = false
        }
    }

    override fun navigateToTvShowDetails(tvShowModel: TvShowModel) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Navigator.startTvShowDetailsWithSharedElement(activity as Activity, tvShowModel, transitionView)
        } else {
            Navigator.startTvShowDetails(activity as Activity, tvShowModel)
        }
    }

    override fun populateRecyclerList(premiereDatesModel: PremiereDatesModel?) {
        // If pull to refresh we need to clear view model and re init recycler adapter
        if (swipe_refresh_layout_premieres.isRefreshing) {
            swipe_refresh_layout_premieres.isRefreshing = false
            initRecyclerAdapter()
            premiereDatesModel?.let { viewModel.clearAndRetainModel(it) }
        } else {
            premiereDatesModel?.let { viewModel.retainModel(it) }
        }
        recyclerAdapter.addItems(viewModel.premiereList, viewModel.isLast)
    }

    override fun initSwipeRefreshLayout() = with(swipe_refresh_layout_premieres) {
        setProgressViewEndTarget(true, 400)
        setOnRefreshListener(this@PremieresFragment)
    }

    override fun initRecyclerAdapter() {
        recyclerAdapter = PremieresRecyclerAdapter(this, this)
        recycler_view_premieres.apply {
            setHasFixedSize(true)
            val linearLayout = LinearLayoutManager(context)
            layoutManager = linearLayout
            addOnScrollListener(OnScrollHideBottomNavigationListener((activity as MainFeedActivity).navigation_bottom_layout))
            addOnScrollListener(OnScrollHideFabButtonListener((activity as MainFeedActivity).fab_button_main_feed_search))
            // When scroll to end of list presenter load next page of most popular
            addOnScrollListener(InfiniteScrollListener({
                if (!viewModel.isLast) {
                    presenter.requestPremiereDates(++viewModel.page, false)
                }
            }, linearLayout))
            adapter = recyclerAdapter
        }
    }

    private fun setLandscapeNavBarPaddingEnd(vararg views: View) = views.forEach {
        with(it) {
            setPadding(paddingStart, paddingTop, (paddingEnd + activity!!.getNavigationBarSize().x), paddingBottom)
        }
    }
}
