package pl.hypeapp.episodie.ui.features.seasons

import android.util.Log
import android.view.View
import android.view.ViewStub
import android.widget.Button
import butterknife.BindView
import butterknife.ButterKnife
import com.airbnb.lottie.LottieAnimationView
import pl.hypeapp.episodie.R
import pl.hypeapp.episodie.extensions.viewGone
import pl.hypeapp.episodie.extensions.viewVisible

internal class SeasonsLayoutState(val view: View?) {

    @BindView(R.id.text_view_fragment_episodes_empty_seasons)
    lateinit var emptySeasonMessage: View

    @BindView(R.id.view_error_fragment_seasons)
    lateinit var errorView: ViewStub

    @BindView(R.id.view_loading_fragment_seasons)
    lateinit var loadingView: ViewStub

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
        view?.findViewById<LottieAnimationView>(R.id.animation_view_item_loading)?.cancelAnimation()
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

    fun onError(function: () -> Unit) {
        refreshView.viewGone()
        emptySeasonMessage.viewGone()
        loadingView.viewGone()
        errorView.viewVisible()
        view?.findViewById<Button>(R.id.button_item_error_retry)?.setOnClickListener {
            function()
            Log.e("CLICK", "CLICK")
        }
    }

    fun startLoadingAnimation() {
        view?.findViewById<LottieAnimationView>(R.id.animation_view_item_loading)?.playAnimation()
    }

}
