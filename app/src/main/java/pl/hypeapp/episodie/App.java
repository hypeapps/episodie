package pl.hypeapp.episodie;

import android.app.Application;

import pl.hypeapp.episodie.login.DaggerLoginComponent;
import pl.hypeapp.episodie.login.LoginComponent;
import pl.hypeapp.episodie.network.firebase.FirebaseAuthModule;

public class App extends Application{

    private LoginComponent loginComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        loginComponent = DaggerLoginComponent.builder()
                .appModule(new AppModule(this))
//                .firebaseAuthModule(new FirebaseAuthModule())
                .build();
    }



    public LoginComponent getLoginComponent(){
        return loginComponent;
    }
}
