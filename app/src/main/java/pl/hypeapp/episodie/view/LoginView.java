package pl.hypeapp.episodie.view;

import android.widget.ImageView;

import com.hannesdorfmann.mosby.mvp.MvpView;

public interface LoginView extends MvpView {

    void loadImageFromUrlIntoView(ImageView view, String url);
}
