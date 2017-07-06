package pl.hypeapp.episodie.ui.features.mainfeed.listener

import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.RecyclerView


class OnScrollHideFabButton(val fabButton: FloatingActionButton) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        if (dy > 6) {
            fabButton.hide()
        } else if (dy < -6) {
            fabButton.show()
        }
    }

}
