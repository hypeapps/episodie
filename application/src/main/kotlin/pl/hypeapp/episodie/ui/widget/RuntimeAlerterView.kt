package pl.hypeapp.episodie.ui.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.hanks.htextview.evaporate.EvaporateTextView
import pl.hypeapp.episodie.R
import pl.hypeapp.episodie.extensions.getFullRuntimeFormatted
import pl.hypeapp.episodie.extensions.setFullRuntime
import pl.hypeapp.episodie.extensions.viewVisible

class RuntimeAlerterView(context: Context, attributeSet: AttributeSet) : FrameLayout(context, attributeSet) {

    private var text: EvaporateTextView

    private var icon: ImageView

    private var runtimeDiffText: TextView

    private var slideInUpAnimation: Animation

    init {
        inflate(context, R.layout.view_runtime_alerter, this)
        isHapticFeedbackEnabled = true
        text = findViewById(R.id.view_alerter_text)
        icon = findViewById(R.id.view_alerter_icon)
        runtimeDiffText = findViewById(R.id.view_alerter_runtime_diff)
        this.visibility = View.INVISIBLE
        slideInUpAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.anim_slide_in_up)
    }

    fun show(oldRuntime: Long, newRuntime: Long) {
        text.animateText(getFullRuntimeFormatted(resources, oldRuntime))
        postDelayed({
            YoYo.with(Techniques.SlideInDown)
                    .duration(700)
                    .interpolate(AnimationUtils.loadInterpolator(context, R.anim.interpolator_overshoot))
                    .onStart {
                        this@RuntimeAlerterView.viewVisible()
                        icon.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_pulse))
                    }
                    .onEnd {
                        text.animateText(getFullRuntimeFormatted(resources, newRuntime))
                        startSlideInUpAnimation()
                        startTakingOffDiffAnimation(newRuntime - oldRuntime)
                    }
                    .playOn(this)
        }, 400)
    }

    @SuppressLint("SetTextI18n")
    private fun startTakingOffDiffAnimation(runtimeDiff: Long) {
        if (runtimeDiff < 0) {
            runtimeDiffText.setFullRuntime(runtimeDiff.unaryMinus())
            runtimeDiffText.text = "- ${runtimeDiffText.text}"
        } else {
            runtimeDiffText.setFullRuntime(runtimeDiff)
            runtimeDiffText.text = "+ ${runtimeDiffText.text}"
        }
        YoYo.with(Techniques.TakingOff)
                .onStart { runtimeDiffText.viewVisible() }
                .playOn(runtimeDiffText)
    }

    private fun startSlideInUpAnimation() {
        animation = slideInUpAnimation
        slideInUpAnimation.startOffset = 700
        slideInUpAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(p0: Animation?) {}

            override fun onAnimationEnd(p0: Animation?) {
                this@RuntimeAlerterView.visibility = View.INVISIBLE
                icon.clearAnimation()
            }

            override fun onAnimationStart(p0: Animation?) {}
        })
        slideInUpAnimation.start()
    }

}
