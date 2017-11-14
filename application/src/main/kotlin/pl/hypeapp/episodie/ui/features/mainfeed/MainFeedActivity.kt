package pl.hypeapp.episodie.ui.features.mainfeed

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import butterknife.OnClick
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_main_feed.fab_button_main_feed_search
import kotlinx.android.synthetic.main.activity_main_feed.view_pager
import kotlinx.android.synthetic.main.bottom_navigation_view.*
import kotlinx.android.synthetic.main.toolbar_feed.*
import kotlinx.android.synthetic.main.toolbar_feed.toolbar_feed
import pl.hypeapp.episodie.App
import pl.hypeapp.episodie.R
import pl.hypeapp.episodie.di.components.ActivityComponent
import pl.hypeapp.episodie.di.components.DaggerActivityComponent
import pl.hypeapp.episodie.extensions.getNavigationBarSize
import pl.hypeapp.episodie.extensions.getRealScreenSize
import pl.hypeapp.episodie.extensions.getStatusBarHeight
import pl.hypeapp.episodie.extensions.isLandscapeOrientation
import pl.hypeapp.episodie.ui.base.BaseActivity
import pl.hypeapp.episodie.ui.features.navigationdrawer.NavigationDrawer
import pl.hypeapp.presentation.mainfeed.MainFeedPresenter
import pl.hypeapp.presentation.mainfeed.MainFeedView
import javax.inject.Inject

class MainFeedActivity : BaseActivity(), MainFeedView {

    override fun getLayoutRes(): Int = R.layout.activity_main_feed

    lateinit var navigationDrawer: NavigationDrawer

    private lateinit var toolbarTitleAnimation: ToolbarTitleAnimation

    val onActivityReenterSubject: PublishSubject<Int> = PublishSubject.create()

    @Inject
    lateinit var presenter: MainFeedPresenter

    private val component: ActivityComponent
        get() = DaggerActivityComponent.builder()
                .appComponent((application as App).component)
                .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        presenter.onAttachView(this)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        setNavigationBarBackgroundHeight()
        initToolbar()
        initPagerAdapter()
        navigationDrawer = NavigationDrawer(this, toolbar_feed)
        lifecycle.addObserver(navigationDrawer)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        toolbarTitleAnimation.start()
    }

    override fun onActivityReenter(resultCode: Int, data: Intent?) {
        super.onActivityReenter(resultCode, data)
        onActivityReenterSubject.onNext(resultCode)
    }

    override fun onPause() {
        super.onPause()
        toolbarTitleAnimation.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetachView()
    }

    override fun navigateToPage(page: Int) {
        view_pager.currentItem = page
    }

    override fun navigateToSearch() {
        TODO("not implemented")
    }

    override fun addFabButtonLandscapePadding() = with(fab_button_main_feed_search) {
        val screenWidth = getRealScreenSize().x
        if (isLandscapeOrientation() && getNavigationBarSize().x != screenWidth) {
            val params: ViewGroup.MarginLayoutParams = layoutParams as ViewGroup.MarginLayoutParams
            params.marginEnd = params.marginEnd + getNavigationBarSize().x
            layoutParams = params
        }
    }

    @OnClick(R.id.toolbar_feed_home_button)
    fun onToolbarHomeButtonClick() {
        navigationDrawer.toggleDrawer()
    }

    private fun initToolbar() = with(toolbar_feed) {
        setSupportActionBar(this)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        setPadding(paddingLeft, resources.getStatusBarHeight(), paddingRight, paddingBottom)
        toolbarTitleAnimation = ToolbarTitleAnimation(animation_view_toolbar_feed_title)
    }

    private fun setNavigationBarBackgroundHeight() = with(navigation_bottom_view) {
        if (!this@MainFeedActivity.isLandscapeOrientation()) {
            navigation_bar_background.layoutParams.height = getNavigationBarSize().y
        } else {
            setPadding(paddingLeft, paddingTop, (paddingEnd + getNavigationBarSize().x), paddingBottom)
        }
        val screenWidth = getRealScreenSize().x
        if (this@MainFeedActivity.isLandscapeOrientation() && getNavigationBarSize().x == screenWidth) {
            setPadding(paddingLeft, paddingTop, 0, paddingBottom)
            navigation_bar_background.layoutParams.height = getNavigationBarSize().y
        }
    }

    private fun initPagerAdapter() {
        view_pager.adapter = MainFeedPagerAdapter(supportFragmentManager)
        view_pager.offscreenPageLimit = 1
    }

}
