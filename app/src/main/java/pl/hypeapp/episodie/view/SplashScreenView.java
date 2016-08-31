package pl.hypeapp.episodie.view;

import android.widget.ImageView;

import com.hannesdorfmann.mosby.mvp.MvpView;

public interface SplashScreenView extends MvpView{

    void runActivity(Class startActivityClass);

    void loadImageFromUrlIntoView(ImageView view, String url);
}
