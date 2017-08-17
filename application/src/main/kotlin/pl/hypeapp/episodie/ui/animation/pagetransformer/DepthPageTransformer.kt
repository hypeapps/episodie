package pl.hypeapp.episodie.ui.animation.pagetransformer

import android.view.View

class DepthPageTransformer : BaseTransformer() {

    override fun onTransform(view: View, position: Float) = with(view) {
        if (position <= 0f) {
            translationX = 0f
            scaleX = 1f
            scaleY = 1f
        } else if (position <= 1f) {
            val scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position))
            alpha = 1 - position
            pivotY = 0.5f * view.height
            translationX = view.width * -position
            scaleX = scaleFactor
            scaleY = scaleFactor
        }
    }

    override fun isPagingEnabled(): Boolean = true

    private companion object {
        private val MIN_SCALE = 0.75f
    }

}
