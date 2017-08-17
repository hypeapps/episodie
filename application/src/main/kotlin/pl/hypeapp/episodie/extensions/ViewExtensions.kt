package pl.hypeapp.episodie.extensions

import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.Target
import pl.hypeapp.episodie.glide.GlideApp

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}

fun ImageView.loadImage(url: String?): Target<Drawable> =
        GlideApp.with(this)
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(this)

fun ImageView.loadDrawableResource(drawableResource: Int): Target<Drawable> =
        GlideApp.with(this)
                .load(drawableResource)
                .centerCrop()
                .into(this)

// We need to play games with padding to proper displayed recycler view
fun RecyclerView.setRecyclerViewPadding(insetPaddingTop: Boolean) {
    val top = if (insetPaddingTop) (resources.getStatusBarHeight() + context.getActionBarSize()) else 0
    var right = paddingRight
    var bottom = paddingBottom
    // If orientation is landscape addItems navigation bar size to padding end
    if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        right = context.getNavigationBarSize().x
    }
    val screenWidth = context.getRealScreenSize().x
    // If orientation is landscape and nav bar size is equals to width screen addItems navigation bar y size
    // to padding bottom
    if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE &&
            context.getNavigationBarSize().x == screenWidth) {
        right = paddingRight
        bottom = context.getNavigationBarSize().y
    }
    setPadding(paddingStart, top, right, bottom)
}
