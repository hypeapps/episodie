package pl.hypeapp.episodie.ui.widget

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Typeface
import android.support.design.widget.TabLayout
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.TextView
import pl.hypeapp.episodie.R

class CustomFontTabLayout : TabLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initFont(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initFont(attrs)
    }

    private lateinit var typeFace: Typeface

    override fun addTab(tab: Tab, position: Int, setSelected: Boolean) {
        super.addTab(tab, position, setSelected)
        val rootLayout: ViewGroup = getChildAt(0) as ViewGroup
        val tabView: ViewGroup = rootLayout.getChildAt(tab.position) as ViewGroup
        val tabTextView: TextView = tabView.getChildAt(1) as TextView
        tabTextView.setTypeface(typeFace, Typeface.NORMAL)
    }

    private fun initFont(attrs: AttributeSet) {
        val typedArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomFontTabLayout)
        val fontPath: String? = typedArray.getString(R.styleable.CustomFontTabLayout_fontPath)
        typedArray.recycle()
        if (fontPath != null) {
            typeFace = Typeface.createFromAsset(context.assets, fontPath)
        } else {
            typeFace = Typeface.DEFAULT
        }
    }

}
