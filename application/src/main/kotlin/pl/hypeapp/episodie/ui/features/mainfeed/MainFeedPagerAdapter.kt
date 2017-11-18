package pl.hypeapp.episodie.ui.features.mainfeed

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import pl.hypeapp.episodie.ui.features.mostpopular.MostPopularFragment
import pl.hypeapp.episodie.ui.features.premieres.PremieresFragment
import pl.hypeapp.episodie.ui.features.top.TopListFragment

class MainFeedPagerAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            PAGE_MOST_POPULAR -> MostPopularFragment()
            PAGE_TOP_LIST -> TopListFragment()
            PAGE_PREMIERES -> PremieresFragment()
            else -> throw IllegalStateException("Wrong fragment index")
        }
    }

    override fun getCount(): Int {
        return NUM_PAGES
    }

    companion object {
        val PAGE_MOST_POPULAR = 0
        val PAGE_TOP_LIST = 1
        val PAGE_PREMIERES = 2
        val NUM_PAGES = 3
    }

}
