package pl.hypeapp.episoder.ui.presenter;

import android.net.Uri;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import pl.hypeapp.episoder.ui.activity.LoginActivity;
import pl.hypeapp.episoder.view.SplashScreenView;

public class SplashScreenPresenter extends MvpBasePresenter<SplashScreenView> {

    public void loadBackgroundImage(ImageView backgroundImageView){
        String url = "http://www.goliath.com/wp-content/uploads/2015/12/1035x776-breakingbad-1800-1404309469.jpg";
        getView().loadImageIntoView(backgroundImageView, getView().loadImageFromUrl(url));
    }

    public void runActivityWithDelay(long delayInMilis){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isViewAttached()) {
                    getView().runActivity(LoginActivity.class);
                }
            }
        }, delayInMilis);
    }
}
