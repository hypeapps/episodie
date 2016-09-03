package pl.hypeapp.episodie;

import android.app.Application;

import pl.hypeapp.episodie.network.firebase.FirebaseAuthModule;

public class App extends Application{

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .firebaseAuthModule(new FirebaseAuthModule())
                .build();
    }

    public AppComponent getAuthComponent(){
        return appComponent;
    }
}
