package pl.hypeapp.episodie.ui.features.mainfeed.listener

import android.support.v7.widget.RecyclerView
import android.view.View
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo

class OnScrollHideBottomNavigationListener(var bottomNavigation: View) : RecyclerView.OnScrollListener() {

    var isScrolledDown = false

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        if (dy > 6) {
            if (!isScrolledDown)
                YoYo.with(Techniques.SlideOutDown)
                        .duration(200)
                        .onStart { isScrolledDown = true }
                        .playOn(bottomNavigation)
        } else if (dy < -6) {
            if (isScrolledDown)
                YoYo.with(Techniques.SlideInUp)
                        .duration(200)
                        .onStart { isScrolledDown = false }
                        .playOn(bottomNavigation)
        }
    }
}
