package pl.hypeapp.episodie.ui.base

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle

abstract class BaseViewModelActivity<T : ViewModel> : BaseActivity() {

    protected abstract val viewModelClass: Class<T>

    protected lateinit var viewModel: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(viewModelClass)
    }

}
