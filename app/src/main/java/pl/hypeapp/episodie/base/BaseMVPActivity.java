package pl.hypeapp.episodie.base;

import android.app.Activity;
import android.widget.TextView;

import com.tinmegali.mvp.mvp.GenericMVPActivity;
import com.tinmegali.mvp.mvp.PresenterOps;

import pl.hypeapp.episodie.util.FontManager;

public abstract class BaseMVPActivity<RequiredViewOps, ProvidedPresenterOps, PresenterType extends PresenterOps<RequiredViewOps>>
        extends GenericMVPActivity<RequiredViewOps, ProvidedPresenterOps, PresenterType>
        implements BaseActivityView {

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void setTextLogoFont(TextView textView) {
        if(textView!= null) {
            textView.setTypeface(FontManager.getInstance(getAssets()).getFont("fonts/coolvetica.ttf"));
        }
    }
}
