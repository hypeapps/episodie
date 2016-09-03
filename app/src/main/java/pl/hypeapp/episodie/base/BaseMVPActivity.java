package pl.hypeapp.episodie.base;

import android.app.Activity;

import com.tinmegali.mvp.mvp.GenericMVPActivity;
import com.tinmegali.mvp.mvp.PresenterOps;

public abstract class BaseMVPActivity<RequiredViewOps, ProvidedPresenterOps, PresenterType extends PresenterOps<RequiredViewOps>>
        extends GenericMVPActivity<RequiredViewOps, ProvidedPresenterOps, PresenterType>
        implements BaseActivityView {

    @Override
    public Activity getActivity() {
        return this;
    }
}
