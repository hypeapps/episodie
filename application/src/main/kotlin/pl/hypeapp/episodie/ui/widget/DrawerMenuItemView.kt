package pl.hypeapp.episodie.ui.widget

import android.content.Context
import android.content.res.TypedArray
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import pl.hypeapp.episodie.R

class DrawerMenuItemView(context: Context, attributeSet: AttributeSet) : ConstraintLayout(context, attributeSet) {

    private var title: TextView

    private val icon: ImageView

    init {
        val typedArray: TypedArray = context.obtainStyledAttributes(attributeSet, R.styleable.DrawerMenuItemView)
        val iconSrc: Drawable? = typedArray.getDrawable(R.styleable.DrawerMenuItemView_iconSrc)
        val text: String? = typedArray.getString(R.styleable.DrawerMenuItemView_text)
        typedArray.recycle()

        val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.drawer_menu_item_view, this, true)

        icon = getChildAt(0) as ImageView
        title = getChildAt(1) as TextView

        iconSrc?.let { icon.setImageDrawable(iconSrc) }
        text?.let { title.text = text }
        setInactive()
    }

    fun setActive() {
        icon.setColorFilter(ContextCompat.getColor(context, android.R.color.white), PorterDuff.Mode.SRC_IN)
        title.setTextColor(ContextCompat.getColor(context, android.R.color.white))
    }

    fun setInactive() {
        icon.setColorFilter(ContextCompat.getColor(context, R.color.drawer_item_inactive), PorterDuff.Mode.SRC_IN)
        title.setTextColor(ContextCompat.getColor(context, R.color.drawer_item_inactive))
    }

}
