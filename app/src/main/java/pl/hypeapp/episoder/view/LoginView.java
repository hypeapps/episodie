package pl.hypeapp.episoder.view;

import android.widget.ImageView;

import com.hannesdorfmann.mosby.mvp.MvpView;

public interface LoginView extends MvpView {

    void loadImageFromUrlIntoView(ImageView view, String url);
}
