package pl.hypeapp.episodie.adapter

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

class InfiniteScrollListener(val func: () -> Unit,
                             val layoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {

    private var previousTotal = 0

    private var loading = true
    // Indicates on which visible element should start loading next items.
    private var visibleThreshold = 0

    private var firstVisibleItem = 0

    private var visibleItemCount = 0

    private var totalItemCount = 0

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dy > 0) {
            visibleItemCount = recyclerView.childCount
            totalItemCount = layoutManager.itemCount
            firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

            if (loading) {
                if (totalItemCount > previousTotal) {
                    loading = false
                    previousTotal = totalItemCount
                }
            }
            if (!loading && (totalItemCount - visibleItemCount)
                    <= (firstVisibleItem + visibleThreshold)) {
                // End has been reached, start loading next items.
                func()
                loading = true
            }
        }
    }

}