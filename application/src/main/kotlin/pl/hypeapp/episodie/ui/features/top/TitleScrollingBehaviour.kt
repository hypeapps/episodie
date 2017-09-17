package pl.hypeapp.episodie.ui.features.top

import android.content.Context
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import pl.hypeapp.episodie.R

class TitleScrollingBehaviour(context: Context, attrs: AttributeSet) : AppBarLayout.ScrollingViewBehavior(context, attrs) {

    override fun layoutDependsOn(parent: CoordinatorLayout?, child: View?, dependency: View?): Boolean {
        super.layoutDependsOn(parent, child, dependency)
        return dependency is AppBarLayout
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout?, child: View?, dependency: View?): Boolean {
        val scrollPercentage = (dependency?.y!!.toFloat() / dependency.height) * -1
        with(dependency.findViewById<TextView>(R.id.text_view_top_list_appbar_title)) {
            scaleY = 1f - scrollPercentage / 2.5f
            scaleX = 1f - scrollPercentage / 2.5f
        }
        return super.onDependentViewChanged(parent, child, dependency)
    }

}
