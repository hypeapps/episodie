package pl.hypeapp.episodie.ui.animation.pagetransformer

/*
 * Copyright 2014 Toxic Bakery
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import android.support.v4.view.ViewPager.PageTransformer
import android.view.View

abstract class BaseTransformer : PageTransformer {

    /**
     * Called each {@link #transformPage(android.view.View, float)}.
     *
     * @param view
     * @param position
     */
    protected abstract fun onTransform(view: View, position: Float)

    override fun transformPage(view: View, position: Float) {
        onPreTransform(view, position)
        onTransform(view, position)
        onPostTransform(view, position)
    }

    /**
     * If the position offset of a fragment is less than negative one or greater than one, returning true will set the
     * visibility of the fragment to {@link android.view.View#GONE}. Returning false will force the fragment to {@link android.view.View#VISIBLE}.
     *
     * @return
     */
    protected fun hideOffscreenPages(): Boolean {
        return true
    }

    /**
     * Indicates if the default animations of the view pager should be used.

     * @return
     */
    protected open fun isPagingEnabled(): Boolean {
        return false
    }

    /**
     * Called each [.transformPage] before {[.onTransform] is called.

     * @param view
     * *
     * @param position
     */
    protected fun onPreTransform(view: View, position: Float) {
        val width = view.width.toFloat()

        view.rotationX = 0f
        view.rotationY = 0f
        view.rotation = 0f
        view.scaleX = 1f
        view.scaleY = 1f
        view.pivotX = 0f
        view.pivotY = 0f
        view.translationY = 0f
        view.translationX = if (isPagingEnabled()) 0f else -width * position

        if (hideOffscreenPages()) {
            view.alpha = if (position <= -1f || position >= 1f) 0f else 1f
        } else {
            view.alpha = 1f
        }
    }

    /**
     * Called each [.transformPage] call after [.onTransform] is finished.

     * @param view
     * *
     * @param position
     */
    protected fun onPostTransform(view: View, position: Float) {}

}
