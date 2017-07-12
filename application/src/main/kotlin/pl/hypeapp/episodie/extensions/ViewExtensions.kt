package pl.hypeapp.episodie.extensions

import android.annotation.SuppressLint
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import pl.hypeapp.episodie.GlideApp
import pl.hypeapp.episodie.R
import java.util.concurrent.TimeUnit.MINUTES

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}

fun TextView.setTextFromHtml(text: String?) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        this.text = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY)
    else
        this.text = Html.fromHtml(text)
}

@SuppressLint("SetTextI18n")
fun TextView.setTvShowRuntime(runtime: Long?) {
    runtime?.let {
        if (runtime <= MINUTES.convert(60, MINUTES)) {
            this.text = "${MINUTES.toMinutes(runtime)}${resources.getStringArray(R.array.time_units)[0]}"
        } else if (runtime <= MINUTES.convert(24 * 60, MINUTES)) {
            this.text = "${MINUTES.toHours(runtime)}${resources.getStringArray(R.array.time_units)[1]}"
        } else {
            this.text = "${MINUTES.toDays(runtime)}${resources.getStringArray(R.array.time_units)[2]}"
        }
    }
}

fun ImageView.loadImage(url: String?) {
    GlideApp.with(this)
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(this)
}

fun ImageView.loadGif(drawable: Int) {
    GlideApp.with(this)
            .asGif()
            .load(drawable)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .listener(object : RequestListener<GifDrawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<GifDrawable>?,
                                          isFirstResource: Boolean): Boolean {
                    return false
                }

                override fun onResourceReady(resource: GifDrawable?, model: Any?, target: Target<GifDrawable>?,
                                             dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    resource?.apply { setLoopCount(1) }
                    return false
                }

            }).into(this)
}
