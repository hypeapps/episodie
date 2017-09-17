package pl.hypeapp.episodie.ui.features.mainfeed

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import pl.hypeapp.episodie.ui.features.mostpopular.MostPopularFragment
import pl.hypeapp.episodie.ui.features.top.TopListFragment

class MainFeedPagerAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        when (position) {
            PAGE_MOST_POPULAR -> return MostPopularFragment()
            PAGE_TOP_LIST -> return TopListFragment()
            else -> throw IllegalStateException("Wrong fragment index")
        }
    }

    override fun getCount(): Int {
        return NUM_PAGES
    }

    private companion object {
        private val PAGE_MOST_POPULAR = 0
        private val PAGE_TOP_LIST = 1
        private val PAGE_PREMIERES = 2
        private val NUM_PAGES = 2
    }

}
