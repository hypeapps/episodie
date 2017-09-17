package pl.hypeapp.episodie.ui.base

import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.LifecycleRegistryOwner
import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife
import pl.hypeapp.episodie.ui.animation.SmallBangAnimator
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

@Suppress("LeakingThis")
abstract class BaseActivity : AppCompatActivity(), LifecycleRegistryOwner {

    @LayoutRes
    protected abstract fun getLayoutRes(): Int

    private val lifecycleRegistry: LifecycleRegistry = LifecycleRegistry(this)

    override fun getLifecycle(): LifecycleRegistry = lifecycleRegistry

    protected lateinit var smallBangAnimator: SmallBangAnimator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutRes())
        ButterKnife.bind(this)
    }

    override fun onStart() {
        super.onStart()
        smallBangAnimator = SmallBangAnimator(this)
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

}
