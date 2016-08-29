package pl.hypeapp.episoder.ui.presenter;

import android.os.Handler;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import pl.hypeapp.episoder.ui.activity.LoginActivity;
import pl.hypeapp.episoder.view.SplashScreenView;

public class SplashScreenPresenter extends MvpBasePresenter<SplashScreenView> {

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
