package pl.hypeapp.episodie.ui.widget

/*
 * Copyright 2015 Laurens Muller.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context
import android.os.Build
import android.os.Handler
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSmoothScroller
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.AbsListView.OnScrollListener.SCROLL_STATE_FLING
import android.widget.AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL
import android.widget.LinearLayout

class SnappingRecyclerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : RecyclerView(context, attrs, defStyleAttr) {

    private var _userScrolling = false

    private var _scrolling = false

    private var _scrollState = RecyclerView.SCROLL_STATE_IDLE

    private var _lastScrollTime: Long = 0

    private val mHandler = Handler()

    private var _scaleViews = false

    private var _orientation = Orientation.HORIZONTAL

    private var _childViewMetrics: ChildViewMetrics? = null

    private var _listener: OnViewSelectedListener? = null

    /**
     * Returns the currently centered item aka the selected item
     */
    var selectedPosition: Int = 0
        private set

    init {
        init()
    }

    private fun init() {
        setHasFixedSize(true)
        setOrientation(_orientation)
        enableSnapping()
    }

    private var scrolling: Boolean = false

    private var isFirstAttach: Boolean = true

    override fun onChildAttachedToWindow(child: View?) {
        super.onChildAttachedToWindow(child)
        if (!scrolling && _scrollState == RecyclerView.SCROLL_STATE_IDLE) {
            updateViews()
            if (isFirstAttach) {
                postDelayed({
                    smoothScrollToLastChild()
                }, 300)
                isFirstAttach = false
            }
        }
    }

    fun scrollToLastChild() {
        if (!isFirstAttach) {
            postDelayed({
                smoothScrollToLastChild()
            }, 300)
        }
    }

    fun scrollToCenterView() {
        scrollToView(centerView)
        postDelayed({
            updateViews()
            centerView?.let {
                smoothScrollBy(getScrollDistance(it))
            }
        }, 300)
    }

    private fun enableSnapping() {
        viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                viewTreeObserver.removeGlobalOnLayoutListener(this)
            }
        })

        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                updateViews()
                super.onScrolled(recyclerView, dx, dy)
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                /** if scroll is caused by a touch (scroll touch, not any touch)  */
                if (newState == SCROLL_STATE_TOUCH_SCROLL) {
                    /** if scroll was initiated already, it would probably be a tap  */
                    /** if scroll was not initiated before, this is probably a user scrolling  */
                    if (!_scrolling) {
                        _userScrolling = true
                    }
                } else if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    /** if user is the one scrolling, snap to the view closest to center  */
                    if (_userScrolling) {
                        scrollToView(centerView)
                    }

                    _userScrolling = false
                    _scrolling = false

                    /** if idle, always check location and correct it if necessary, this is just an extra check  */
                    if (centerView != null && getPercentageFromCenter(centerView) > 0) {
                        scrollToView(centerView)
                    }

                    /** if idle, notify listeners of new selected view  */
                    notifyListener()
                } else if (newState == SCROLL_STATE_FLING) {
                    _scrolling = true
                }

                _scrollState = newState
            }
        })
    }

    private fun notifyListener() {
        val view = centerView
        val position = getChildAdapterPosition(view)

        /** if there is a listener and the index is not the same as the currently selected position, notify listener  */
        if (_listener != null && position != selectedPosition) {
            _listener!!.onSelected(view, position)
        }

        selectedPosition = position
    }

    /**
     * Set the orientation for this SnappingRecyclerView
     * @param orientation LinearLayoutManager.HORIZONTAL or LinearLayoutManager.VERTICAL
     */
    fun setOrientation(orientation: Orientation) {
        this._orientation = orientation
        _childViewMetrics = ChildViewMetrics(_orientation)
        layoutManager = object : LinearLayoutManager(context, _orientation.intValue(), false) {
            override fun smoothScrollToPosition(recyclerView: RecyclerView?, state: RecyclerView.State?, position: Int) {
                val smoothScroller = object : LinearSmoothScroller(recyclerView?.context) {
                    override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics?): Float {
                        return 200f / displayMetrics?.densityDpi!!
                    }
                }
                smoothScroller.targetPosition = position
                startSmoothScroll(smoothScroller)
            }
        }
    }

    /**
     * Set the OnViewSelectedListener
     * @param listener the OnViewSelectedListener
     */
    fun setOnViewSelectedListener(listener: OnViewSelectedListener) {
        this._listener = listener
    }

    /**
     * Enable downscaling of views which are not focused, based on how far away they are from the center
     * @param enabled enable or disable the scaling behaviour
     */
    fun enableViewScaling(enabled: Boolean) {
        this._scaleViews = enabled
    }

    private fun updateViews() {
        for (i in 0..childCount - 1) {
            val child = getChildAt(i)
            setMarginsForChild(child)

            if (_scaleViews) {
                val percentage = getPercentageFromCenter(child)
                val scale = 1f - 0.7f * percentage

                child.scaleX = scale
                child.scaleY = scale
            }
        }
    }

    /**
     * Adds the margins to a childView so a view will still center even if it's only a single child
     * @param child childView to set margins for
     */
    private fun setMarginsForChild(child: View) {
        val lastItemIndex = layoutManager.itemCount - 1
        val childIndex = getChildAdapterPosition(child)

        var startMargin = 0
        var endMargin = 0
        var topMargin = 0
        var bottomMargin = 0

        if (_orientation == Orientation.VERTICAL) {
            topMargin = if (childIndex == 0) centerLocation else 0
            bottomMargin = if (childIndex == lastItemIndex) centerLocation else 0
        } else {
            startMargin = if (childIndex == 0) centerLocation else 0
            endMargin = if (childIndex == lastItemIndex) centerLocation else 0
        }

        /** if sdk minimum level is 17, set RTL margins  */
        if (_orientation == Orientation.HORIZONTAL && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            (child.layoutParams as ViewGroup.MarginLayoutParams).setMarginStart(startMargin)
            (child.layoutParams as ViewGroup.MarginLayoutParams).setMarginEnd(endMargin)
        }

        /** If layout direction is RTL, swap the margins   */
        if (ViewCompat.getLayoutDirection(child) == ViewCompat.LAYOUT_DIRECTION_RTL) {
            (child.layoutParams as ViewGroup.MarginLayoutParams).setMargins(endMargin, topMargin, startMargin, bottomMargin)
        } else {
            (child.layoutParams as ViewGroup.MarginLayoutParams).setMargins(startMargin, topMargin, endMargin, bottomMargin)
        }

        /** if sdk minimum level is 18, check if view isn't undergoing a layout pass (this improves the feel of the view by a lot)  */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            if (!child.isInLayout)
                child.requestLayout()
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        val currentTime = System.currentTimeMillis()

        /** if touch events are being spammed, this is due to user scrolling right after a tap,
         * so set userScrolling to true  */
        if (_scrolling && _scrollState == SCROLL_STATE_TOUCH_SCROLL) {
            if (currentTime - _lastScrollTime < MINIMUM_SCROLL_EVENT_OFFSET_MS) {
                _userScrolling = true
            }
        }

        _lastScrollTime = currentTime

        val location = if (_orientation == Orientation.VERTICAL) event.y.toInt() else event.x.toInt()

        val targetView = getChildClosestToLocation(location)

        if (!_userScrolling) {
            if (event.action == MotionEvent.ACTION_UP) {
                if (targetView !== centerView) {
                    scrollToView(targetView)
                    return true
                }
            }
        }

        return super.dispatchTouchEvent(event)
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        val location = if (_orientation == Orientation.VERTICAL) event.y.toInt() else event.x.toInt()

        val targetView = getChildClosestToLocation(location)

        return if (targetView !== centerView) {
            true
        } else super.onInterceptTouchEvent(event)

    }

    private fun getChildClosestToLocation(location: Int): View? {
        if (childCount <= 0)
            return null

        var closestPos = 9999
        var closestChild: View? = null

        for (i in 0..childCount - 1) {
            val child = getChildAt(i)

            val childCenterLocation = _childViewMetrics!!.center(child).toInt()
            val distance = childCenterLocation - location

            /** if child center is closer than previous closest, set it as closest child   */
            if (Math.abs(distance) < Math.abs(closestPos)) {
                closestPos = distance
                closestChild = child
            }
        }

        return closestChild
    }

    /**
     * Check if the view is correctly centered (allow for 10px offset)
     * @param child the child view
     * @return true if correctly centered
     */
    private fun isChildCorrectlyCentered(child: View): Boolean {
        val childPosition = _childViewMetrics!!.center(child).toInt()
        return childPosition > centerLocation - 10 && childPosition < centerLocation + 10
    }

    private val centerView: View?
        get() = getChildClosestToLocation(centerLocation)

    fun scrollToView(child: View?) {
        if (child == null)
            return

        stopScroll()

        val scrollDistance = getScrollDistance(child)

        if (scrollDistance != 0)
            smoothScrollBy(scrollDistance)
    }

    private fun smoothScrollToLastChild() {
        if (selectedPosition + 1 in adapter.itemCount - 1..adapter.itemCount + 1) {
            scrollToView(getChildAt(childCount - 1))
        } else {
            smoothScrollToPosition(layoutManager.itemCount)
        }
    }

    private fun getScrollDistance(child: View): Int {
        val childCenterLocation = _childViewMetrics!!.center(child).toInt()
        return childCenterLocation - centerLocation
    }

    private fun getPercentageFromCenter(child: View?): Float {
        val center = centerLocation.toFloat()
        val childCenter = _childViewMetrics!!.center(child)

        val offSet = Math.max(center, childCenter) - Math.min(center, childCenter)
        val maxOffset = center + _childViewMetrics!!.size(child)

        return offSet / maxOffset
    }

    private val centerLocation: Int
        get() = if (_orientation == Orientation.VERTICAL) measuredHeight / 2 else measuredWidth / 2

    private fun smoothScrollBy(distance: Int) {
        if (_orientation == Orientation.VERTICAL) {
            super.smoothScrollBy(0, distance)
            return
        }

        super.smoothScrollBy(distance, 0)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mHandler.removeCallbacksAndMessages(null)
    }

    private class ChildViewMetrics(private val _orientation: Orientation) {

        fun size(view: View?): Int {
            return if (_orientation == Orientation.VERTICAL) view!!.height else view!!.width

        }

        fun location(view: View?): Float {
            return if (_orientation == Orientation.VERTICAL) view!!.y else view!!.x

        }

        fun center(view: View?): Float {
            return location(view) + size(view) / 2
        }
    }

    enum class Orientation private constructor(internal var value: Int) {
        HORIZONTAL(LinearLayout.HORIZONTAL),
        VERTICAL(LinearLayout.VERTICAL);

        fun intValue(): Int {
            return value
        }
    }

    interface OnViewSelectedListener {
        fun onSelected(view: View?, position: Int)
    }

    companion object {

        private val MINIMUM_SCROLL_EVENT_OFFSET_MS = 20
    }

}
