package pl.hypeapp.episoder.view;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.hannesdorfmann.mosby.mvp.MvpView;

public interface SplashScreenView extends MvpView{

    void runActivity(Class startActivityClass);

    DrawableTypeRequest loadImageFromUrl(String url);

    void loadImageIntoView(ImageView view, DrawableTypeRequest drawableTypeRequest);
}
