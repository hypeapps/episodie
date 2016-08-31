package pl.hypeapp.episodie.ui.presenter;

import android.widget.ImageView;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import pl.hypeapp.episodie.view.LoginView;

public class LoginPresenter extends MvpBasePresenter<LoginView>{

    public void loadBackgroundImage(ImageView backgroundImageView){
        String url = "http://www.goliath.com/wp-content/uploads/2015/12/1035x776-breakingbad-1800-1404309469.jpg";
        getView().loadImageFromUrlIntoView(backgroundImageView, url);
    }
}
