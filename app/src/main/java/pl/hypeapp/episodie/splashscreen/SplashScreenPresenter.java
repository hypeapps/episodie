package pl.hypeapp.episodie.splashscreen;

import android.net.Uri;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
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

    @Override
    public void loadImageFromPathIntoView(ImageView view, String path) {
        Glide.with(activity).load(Uri.parse(path))
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
