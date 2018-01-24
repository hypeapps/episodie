package pl.hypeapp.episodie.ui.features.yourlibrary

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_your_library.*
import pl.hypeapp.episodie.App
import pl.hypeapp.episodie.R
import pl.hypeapp.episodie.di.components.ActivityComponent
import pl.hypeapp.episodie.di.components.DaggerActivityComponent
import pl.hypeapp.episodie.extensions.getStatusBarHeight
import pl.hypeapp.episodie.extensions.isLandscapeOrientation
import pl.hypeapp.episodie.extensions.isNavigationBarLandscape
import pl.hypeapp.episodie.ui.base.BaseViewModelActivity
import pl.hypeapp.episodie.ui.features.navigationdrawer.NavigationDrawer
import pl.hypeapp.episodie.ui.viewmodel.YourLibraryViewModel
import pl.hypeapp.presentation.yourlibrary.YourLibraryPresenter
import pl.hypeapp.presentation.yourlibrary.YourLibraryView
import javax.inject.Inject

class YourLibraryActivity : BaseViewModelActivity<YourLibraryViewModel>(), YourLibraryView {

    override val viewModelClass: Class<YourLibraryViewModel> = YourLibraryViewModel::class.java

    override fun getLayoutRes(): Int = R.layout.activity_your_library

    @Inject
    lateinit var presenter: YourLibraryPresenter

    private lateinit var navigationDrawer: NavigationDrawer

    private val component: ActivityComponent
        get() = DaggerActivityComponent.builder()
                .appComponent((application as App).component)
                .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setNavigationBarOptions()
        component.inject(this)
        presenter.onAttachView(this)
    }

    override fun initNavigationDrawer() {
        toolbar_your_library.apply {
            setSupportActionBar(this)
            supportActionBar?.setHomeButtonEnabled(true)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
            setPadding(paddingLeft, resources.getStatusBarHeight(), paddingRight, paddingBottom)
        }
        navigationDrawer = NavigationDrawer(this, toolbar_your_library)
        lifecycle.addObserver(navigationDrawer)
    }

    private fun setNavigationBarOptions() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        if (isLandscapeOrientation() and !isNavigationBarLandscape()) {
//            setLandscapeNavBarPaddingEnd(constraint_layout_season_tracker)
        }
    }
}
