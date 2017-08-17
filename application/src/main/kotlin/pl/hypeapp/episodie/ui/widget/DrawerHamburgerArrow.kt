package pl.hypeapp.episodie.ui.widget

import android.content.Context
import android.content.res.TypedArray
import android.support.v7.graphics.drawable.DrawerArrowDrawable
import android.util.AttributeSet
import android.widget.ImageView
import pl.hypeapp.episodie.R

class DrawerHamburgerArrow(context: Context, attributeSet: AttributeSet) : ImageView(context, attributeSet) {

    private val drawerArrowDrawable: DrawerArrowDrawable = DrawerArrowDrawable(context)

    init {
        val typedArray: TypedArray = context.obtainStyledAttributes(attributeSet, R.styleable.DrawerHamburgerArrow)
        drawerArrowDrawable.color = typedArray.getColor(R.styleable.DrawerHamburgerArrow_arrow_color, 0xff000000.toInt())
        typedArray.recycle()
        setImageDrawable(drawerArrowDrawable)
    }

    fun setProgress(progress: Float) {
        drawerArrowDrawable.progress = progress
    }

}
