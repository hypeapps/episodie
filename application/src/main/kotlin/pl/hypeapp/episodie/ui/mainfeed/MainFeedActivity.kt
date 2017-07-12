package pl.hypeapp.episodie.ui.mainfeed

import android.os.Bundle
import android.support.v4.view.ViewPager
import butterknife.BindView
import pl.hypeapp.episodie.App
import pl.hypeapp.episodie.R
import pl.hypeapp.episodie.di.components.ActivityComponent
import pl.hypeapp.episodie.di.components.DaggerActivityComponent
import pl.hypeapp.episodie.ui.base.BaseActivity
import pl.hypeapp.presentation.mainfeed.MainFeedPresenter
import pl.hypeapp.presentation.mainfeed.MainFeedView
import javax.inject.Inject

class MainFeedActivity : BaseActivity(), MainFeedView {

    @Inject
    lateinit var presenter: MainFeedPresenter

    @BindView(R.id.view_pager)
    lateinit var viewPager: ViewPager

    private val component: ActivityComponent
        get() = DaggerActivityComponent.builder()
                .appComponent((application as App).component)
                .build()

    override fun getLayoutRes(): Int = R.layout.activity_main_feed

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        presenter.onAttachView(this)
        initPagerAdapter()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetachView()
    }

    override fun showError() {
        TODO("not implemented")
    }

    override fun navigateToPage(page: Int) {
        TODO("not implemented")
    }

    override fun navigateToSearch() {
        TODO("not implemented")
    }

    private fun initPagerAdapter() {
        val mainFeedPagerAdapter: MainFeedPagerAdapter = MainFeedPagerAdapter(supportFragmentManager)
        viewPager.adapter = mainFeedPagerAdapter
    }

}
