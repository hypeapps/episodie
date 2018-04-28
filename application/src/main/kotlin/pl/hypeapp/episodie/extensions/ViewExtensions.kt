package pl.hypeapp.episodie.extensions

import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import pl.hypeapp.domain.model.WatchState
import pl.hypeapp.episodie.R
import pl.hypeapp.episodie.glide.GlideApp

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}

fun ImageView.loadImage(url: String?,
                        scaleType: ImageView.ScaleType = ImageView.ScaleType.FIT_XY): Target<Drawable> =
        GlideApp.with(this)
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.episodie_logo_small)
                .listener(object : RequestListener<Drawable> {
                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        this@loadImage.scaleType = scaleType
                        return false
                    }

                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        this@loadImage.scaleType = ImageView.ScaleType.CENTER_INSIDE
                        return false
                    }
                })
                .into(this)

fun ImageView.loadDrawableResource(drawableResource: Int): Target<Drawable> =
        GlideApp.with(this)
                .load(drawableResource)
                .format(DecodeFormat.PREFER_RGB_565)
                .centerCrop()
                .into(this)

fun ImageView.intoBitmap(bitmap: Bitmap): Target<Drawable> =
        GlideApp.with(this)
                .load(bitmap)
                .into(this)

fun ImageView.loadSharedElement(url: String, loadOnlyFromCache: Boolean = false, onLoadingFinished: () -> Unit = {}) {
    val listener = object : RequestListener<Drawable> {
        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
            onLoadingFinished()
            return false
        }

        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
            onLoadingFinished()
            return false
        }
    }
    val requestOptions = RequestOptions
            .overrideOf(Target.SIZE_ORIGINAL)
            .onlyRetrieveFromCache(loadOnlyFromCache)
    Glide.with(this)
            .load(url)
            .apply(requestOptions)
            .listener(listener)
            .into(this)
}

fun RecyclerView.setActionStatusBarPadding(insetPaddingTop: Boolean) {
    val top = if (insetPaddingTop) (resources.getStatusBarHeight() + context.getActionBarSize()) else 0
    var right = paddingRight
    var bottom = paddingBottom
    if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        right = context.getNavigationBarSize().x
    }
    val screenWidth = context.getRealScreenSize().x
    if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE &&
            context.getNavigationBarSize().x == screenWidth) {
        right = paddingRight
        bottom = context.getNavigationBarSize().y
    }
    setPadding(paddingStart, top, right, bottom)
}

fun View.viewGone() {
    this.visibility = View.GONE
}

fun View.viewVisible() {
    this.visibility = View.VISIBLE
}

fun View.viewInvisible() {
    this.visibility = View.INVISIBLE
}

fun ImageView.manageWatchStateIcon(watchState: String) {
    val diamondDrawable: Int = when (watchState) {
        WatchState.WATCHED -> R.drawable.all_ic_diamond_checked
        WatchState.PARTIALLY_WATCHED -> R.drawable.all_ic_diamond_partially_checked
        else -> R.drawable.all_ic_diamond_unchecked
    }
    this.setImageResource(diamondDrawable)
}

fun FloatingActionButton.manageWatchStateIcon(watchState: String) {
    val diamondDrawable: Int = when (watchState) {
        WatchState.WATCHED -> R.drawable.all_ic_diamond_checked
        WatchState.PARTIALLY_WATCHED -> R.drawable.all_ic_diamond_partially_checked
        else -> R.drawable.all_ic_diamond_unchecked
    }
    this.setImageResource(diamondDrawable)
}
