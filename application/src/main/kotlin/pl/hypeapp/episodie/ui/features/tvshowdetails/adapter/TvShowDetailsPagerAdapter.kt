package pl.hypeapp.episodie.ui.features.tvshowdetails.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import pl.hypeapp.episodie.ui.features.seasons.SeasonsFragment
import pl.hypeapp.episodie.ui.features.tvshowsummary.TvShowSummaryFragment
import pl.hypeapp.episodie.ui.viewmodel.TvShowModelParcelable

class TvShowDetailsPagerAdapter(fm: FragmentManager, private val tvShowModel: TvShowModelParcelable) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            PAGE_ABOUT -> TvShowSummaryFragment.newInstance(tvShowModel)
            PAGE_SEASONS -> SeasonsFragment.newInstance(tvShowModel.id)
            else -> throw IllegalStateException("Invalid fragment index")
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            PAGE_ABOUT -> TITLE_PAGE_ABOUT
            PAGE_SEASONS -> TITLE_PAGE_SEASONS
            else -> throw IllegalStateException("Invalid pager adapter position")
        }
    }

    override fun getCount(): Int = NUM_PAGES

    private companion object {
        val PAGE_ABOUT = 0
        val PAGE_SEASONS = 1
        val NUM_PAGES = 2
        val TITLE_PAGE_ABOUT = "ABOUT"
        val TITLE_PAGE_SEASONS = "EPISODES"
    }

    interface OnChangePageListener : ViewPager.OnPageChangeListener {

        override fun onPageScrollStateChanged(state: Int) {}

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

        override fun onPageSelected(position: Int)

    }

}
