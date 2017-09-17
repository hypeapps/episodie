package pl.hypeapp.episodie.ui.features.mainfeed

import android.animation.ValueAnimator
import com.airbnb.lottie.LottieAnimationView
import pl.hypeapp.episodie.R

class ToolbarTitleAnimation(val toolbarTitleAnimationView: LottieAnimationView,
                            val valueAnimator: ValueAnimator = ValueAnimator.ofFloat(0f, 1f)
                                    .setDuration(2500)) {

    init {
        toolbarTitleAnimationView.setAnimation(toolbarTitleAnimationView.resources.getString(R.string.animation_episodie_text),
                LottieAnimationView.CacheStrategy.Strong)
    }

    fun start() {
        valueAnimator.addUpdateListener { toolbarTitleAnimationView.progress = it.animatedFraction }
        valueAnimator.start()
    }

    fun stop() {
        valueAnimator.end()
    }

}
