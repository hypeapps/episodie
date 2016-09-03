package pl.hypeapp.episodie.splashscreen;

import android.os.Handler;
import android.widget.ImageView;

import com.tinmegali.mvp.mvp.GenericPresenter;

import pl.hypeapp.episodie.login.LoginActivity;
import pl.hypeapp.episodie.util.image.BlurTransformation;
import pl.hypeapp.episodie.util.image.GrayscaleTransformation;

public class SplashScreenPresenter extends GenericPresenter<SplashScreenMVP.RequiredPresenterOps, SplashScreenMVP.ProvidedModelOps,
        SplashScreenMVP.RequiredViewOps, SplashScreenModel>
        implements
        SplashScreenMVP.RequiredPresenterOps,
        SplashScreenMVP.ProvidedPresenterOps {

    private SplashScreenActivity activity;

    @Override
    public void onCreate(SplashScreenMVP.RequiredViewOps view) {
        super.onCreate(SplashScreenModel.class, this);
        setView(view);
        activity = (SplashScreenActivity) getView().getActivity();
    }

    public void loadImageFromUrlIntoView(ImageView view, String url) {
        getModel().getBitmapFromUrl(url, activity)
                .bitmapTransform(new BlurTransformation(activity, 12), new GrayscaleTransformation(activity))
                .into(view);
    }

    public void runActivityWithDelay(long delayInMilis) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getView().runActivity(LoginActivity.class);
            }
        }, delayInMilis);
    }

    @Override
    public void onConfigurationChanged(SplashScreenMVP.RequiredViewOps view) {
        setView(view);
        activity = (SplashScreenActivity) getView().getActivity();
    }

    @Override
    public void onBackPressed() {
    }
}
