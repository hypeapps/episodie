package pl.hypeapp.episodie.ui.features.mainfeed

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import org.buffer.adaptablebottomnavigation.adapter.FragmentStateAdapter
import pl.hypeapp.episodie.ui.features.mostpopular.MostPopularFragment
import pl.hypeapp.episodie.ui.features.premieres.PremieresFragment
import pl.hypeapp.episodie.ui.features.top.TopListFragment

class MainFeedPagerAdapter(fm: FragmentManager?) : FragmentStateAdapter(fm) {

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
        const val PAGE_MOST_POPULAR = 0
        const val PAGE_TOP_LIST = 1
        const val PAGE_PREMIERES = 2
        const val NUM_PAGES = 3
    }

}
