package pl.hypeapp.episodie.ui.base

import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife
import io.reactivex.disposables.Disposable
import pl.hypeapp.episodie.ui.animation.SmallBangAnimator
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

@Suppress("LeakingThis")
abstract class BaseActivity : AppCompatActivity() {

    @LayoutRes
    protected abstract fun getLayoutRes(): Int

    protected lateinit var smallBangAnimator: SmallBangAnimator

    protected var disposable: Disposable? = null

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

    protected fun disposeSubscription() = disposable?.let {
        if (!it.isDisposed) it.dispose()
    }

}
