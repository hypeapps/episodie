package pl.hypeapp.episodie.ui.features.tvshowdetails.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import pl.hypeapp.domain.model.TvShowModel
import pl.hypeapp.episodie.ui.features.seasons.SeasonsFragment
import pl.hypeapp.episodie.ui.features.tvshowinfo.TvShowInfoFragment

class TvShowDetailsPagerAdapter(fm: FragmentManager, private val tvShowModel: TvShowModel?) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        when (position) {
            PAGE_ABOUT -> return TvShowInfoFragment.newInstance(tvShowModel)
            PAGE_EPISODES -> return SeasonsFragment.newInstance(tvShowModel?.id)
            else -> throw IllegalStateException("Invalid fragment index")
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        when (position) {
            PAGE_ABOUT -> return TITLE_PAGE_ABOUT
            PAGE_EPISODES -> return TITLE_PAGE_EPISODES
            else -> throw IllegalStateException("Invalid pager adapter position")
        }
    }

    override fun getCount(): Int = NUM_PAGES

    private companion object {
        val PAGE_ABOUT = 0
        val PAGE_EPISODES = 1
        val NUM_PAGES = 2
        val TITLE_PAGE_ABOUT = "ABOUT"
        val TITLE_PAGE_EPISODES = "EPISODES"
    }

    interface OnChangePageListener : ViewPager.OnPageChangeListener {

        override fun onPageScrollStateChanged(state: Int) {}

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

        override fun onPageSelected(position: Int)

    }

}
