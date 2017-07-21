package pl.hypeapp.episodie.ui.base

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.Unbinder
import pl.hypeapp.episodie.extensions.inflate

abstract class BaseFragment<T : ViewModel> : Fragment() {

    @LayoutRes
    protected abstract fun getLayoutRes(): Int

    protected abstract val viewModelClass: Class<T>

    protected lateinit var viewModel: T

    protected lateinit var unbinder: Unbinder

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View? = container?.inflate(getLayoutRes())
        viewModel = ViewModelProviders.of(this).get(viewModelClass)
        unbinder = ButterKnife.bind(this, view!!)
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder.unbind()
    }

}
