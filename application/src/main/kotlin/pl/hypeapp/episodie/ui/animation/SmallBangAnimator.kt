package pl.hypeapp.episodie.ui.animation

import android.app.Activity
import android.graphics.Color
import android.view.View

class SmallBangAnimator(activity: Activity) {

    private val smallBang: SmallBang = SmallBang.attach2Window(activity)

    init {
        smallBang.setColors(intArrayOf(Color.GRAY, Color.WHITE))
    }

    fun bang(view: View, radius: Float = 100f) {
        smallBang.bang(view, radius, object : SmallBangListener {
            override fun onAnimationEnd() {}

            override fun onAnimationStart() {}
        })
    }

    fun bang(view: View, listener: SmallBangListener) {
        smallBang.bang(view, listener)
    }

}
