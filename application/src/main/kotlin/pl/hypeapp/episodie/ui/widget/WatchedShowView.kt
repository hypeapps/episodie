package pl.hypeapp.episodie.ui.widget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.TargetApi
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.support.constraint.ConstraintLayout
import android.support.v7.graphics.Palette
import android.util.AttributeSet
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateInterpolator
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.facebook.device.yearclass.YearClass
import kotlinx.android.synthetic.main.noise_view_background.view.noise_view
import kotlinx.android.synthetic.main.view_watched_show.view.*
import pl.hypeapp.episodie.R
import pl.hypeapp.episodie.extensions.getFullRuntimeFormatted
import pl.hypeapp.episodie.extensions.isLandscapeOrientation
import pl.hypeapp.episodie.ui.viewmodel.WatchedTvShowViewModel

class WatchedShowView(context: Context, attributeSet: AttributeSet) : ConstraintLayout(context, attributeSet) {

    private lateinit var layoutBackground: Drawable

    private var isNoiseViewInflated: Boolean = false

    init {
        inflate(context, R.layout.view_watched_show, this)
        setWillNotDraw(false)
        if (YearClass.CLASS_2013 < YearClass.get(context.applicationContext)) {
            stub_noise_view.inflate()
            noise_view.noiseIntensity = 0.07f
            isNoiseViewInflated = true
        }
    }

    fun setTvShowDetails(watchedTvShowViewModel: WatchedTvShowViewModel?) = watchedTvShowViewModel?.let {
        text_view_watched_show_title.text = it.tvShow?.name
        YoYo.with(Techniques.ZoomIn).playOn(text_view_watched_show_title)
        setRuntime(watchedTvShowViewModel)
        setWatchedEpisodes(watchedTvShowViewModel)
    }

    fun setWatchedEpisodes(watchedTvShowViewModel: WatchedTvShowViewModel) = text_view_watched_show_episodes.apply {
        text = String.format(resources.getString(R.string.divider_format),
                watchedTvShowViewModel.watchedEpisodes,
                watchedTvShowViewModel.tvShow?.episodeOrder)
    }

    fun setRuntime(watchedTvShowViewModel: WatchedTvShowViewModel) = text_view_watched_show_runtime.apply {
        text = String.format(resources.getString(R.string.divider_format),
                getFullRuntimeFormatted(resources, watchedTvShowViewModel.watchingTime),
                getFullRuntimeFormatted(resources, watchedTvShowViewModel.tvShow?.fullRuntime))
    }

    fun startBitmapFillingAnimation(bitmap: Bitmap) {
        val gradient = fillWithGradient(bitmap)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startGradientRevealAnimation(gradient)
        }
    }

    @Suppress("PLUGIN_WARNING")
    private fun fillWithGradient(bitmap: Bitmap) = GradientDrawable().apply {
        orientation = GradientDrawable.Orientation.BOTTOM_TOP
        gradientType = GradientDrawable.LINEAR_GRADIENT
        Palette.from(bitmap).generate({ palette ->
            colors = intArrayOf(
                    palette.getMutedColor(0x000000),
                    palette.getDominantColor(0x000000))
        })
        //This check is important since in landscape layout there is not card view with cover.
        if (!context.isLandscapeOrientation()) {
            image_view_watch_show_cover.setImageBitmap(bitmap)
            card_view_tv_show_cover.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setInterpolator(AccelerateInterpolator())
                    .setDuration(300)
                    .start()
        }
        layoutBackground = this.mutate().constantState.newDrawable()
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun startGradientRevealAnimation(gradient: GradientDrawable) {
        view_dummy_reveal_animation.background = gradient
        val cx = (view_dummy_reveal_animation.left + view_dummy_reveal_animation.right) / 2
        val y = view_dummy_reveal_animation.height
        val endRadius = Math.hypot(view_dummy_reveal_animation.width.toDouble(), view_dummy_reveal_animation.height.toDouble()).toFloat()
        val circularReveal: Animator = ViewAnimationUtils.createCircularReveal(view_dummy_reveal_animation, cx, y, 0f, endRadius)
        circularReveal.interpolator = AccelerateInterpolator()
        circularReveal.duration = 500L
        circularReveal.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                layout_gradient_background.background = layoutBackground
                view_dummy_reveal_animation.background = null
            }
        })
        circularReveal.start()
    }
}
