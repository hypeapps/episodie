package pl.hypeapp.episodie.ui.base

import android.app.Activity
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.Unbinder
import pl.hypeapp.episodie.extensions.inflate
import pl.hypeapp.episodie.ui.animation.SmallBangAnimator

abstract class BaseFragment : Fragment() {

    @LayoutRes
    protected abstract fun getLayoutRes(): Int

    protected lateinit var unbinder: Unbinder

    protected lateinit var smallBangAnimator: SmallBangAnimator

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View? = container?.inflate(getLayoutRes())
        unbinder = ButterKnife.bind(this, view!!)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        smallBangAnimator = SmallBangAnimator(activity as Activity)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder.unbind()
    }

}
