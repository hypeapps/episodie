package pl.hypeapp.episodie.ui.features.tvshowdetails

import android.content.Context
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.View
import com.github.florent37.diagonallayout.DiagonalLayout
import pl.hypeapp.episodie.R

class TvShowDetailsAppBarBehavior(context: Context, attributeSet: AttributeSet) :
        AppBarLayout.ScrollingViewBehavior(context, attributeSet) {

    private val defaultAngle = 15.0f

    override fun layoutDependsOn(parent: CoordinatorLayout?, child: View?, dependency: View?): Boolean {
        super.layoutDependsOn(parent, child, dependency)
        return dependency is AppBarLayout
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout?, child: View?, dependency: View?): Boolean {
        val scrollPercentage = (dependency as AppBarLayout).y / dependency.totalScrollRange * -1
        val anglePercentage = (defaultAngle / dependency.totalScrollRange * scrollPercentage) * 1000f
        val angleSubtract = defaultAngle - anglePercentage
        handleDiagonalAngle(dependency, angleSubtract)
        handleElevation(dependency, scrollPercentage)
        return super.onDependentViewChanged(parent, child, dependency)
    }

    private fun handleDiagonalAngle(dependency: AppBarLayout, angleSubtract: Float) {
        if (angleSubtract <= defaultAngle) {
            dependency.rootView.findViewById<DiagonalLayout>(R.id.diagonal_layout_tv_show_details).setAngle(angleSubtract)
        }
    }

    private fun handleElevation(dependency: AppBarLayout, scrollPercentage: Float)
            = with(dependency.rootView.findViewById<View>(R.id.tab_layout_tv_show_details) as TabLayout) {
        if (isScrolledOut(scrollPercentage)) {
            ViewCompat.setElevation(this, 36f)
        } else {
            ViewCompat.setElevation(this, 0.1f)
        }
    }

    private fun isScrolledOut(scrollPercentage: Float) = scrollPercentage == 1.0f

}
