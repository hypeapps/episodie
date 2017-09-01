package pl.hypeapp.episodie.ui.features.seasons

import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import pl.hypeapp.episodie.R
import pl.hypeapp.episodie.extensions.viewGone
import pl.hypeapp.episodie.extensions.viewVisible

internal class SeasonsLayoutState(view: View?) {

    @BindView(R.id.text_view_fragment_episodes_empty_seasons)
    lateinit var emptySeasonMessage: View

    @BindView(R.id.view_error_fragment_seasons)
    lateinit var errorView: View

    @BindView(R.id.view_loading_fragment_seasons)
    lateinit var loadingView: View

    @BindView(R.id.swipe_refresh_layout_fragment_seasons)
    lateinit var refreshView: View

    init {
        ButterKnife.bind(this, view!!)
    }

    fun onLoading() {
        refreshView.viewGone()
        emptySeasonMessage.viewGone()
        errorView.viewGone()
        loadingView.viewVisible()
    }

    fun onEmptySeasonMessage() {
        refreshView.viewGone()
        errorView.viewGone()
        loadingView.viewGone()
        emptySeasonMessage.viewVisible()
    }

    fun onShowContent() {
        emptySeasonMessage.viewGone()
        errorView.viewGone()
        loadingView.viewGone()
        refreshView.viewVisible()
    }

    fun onError() {
        refreshView.viewGone()
        emptySeasonMessage.viewGone()
        loadingView.viewGone()
        errorView.viewVisible()
    }

}
