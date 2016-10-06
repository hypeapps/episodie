package pl.hypeapp.episodie;

import javax.inject.Singleton;

import dagger.Component;
import pl.hypeapp.episodie.ui.activity.LoginActivity;
import pl.hypeapp.episodie.network.firebase.FirebaseAuthModule;
import pl.hypeapp.episodie.ui.activity.SignUpActivity;

@Singleton
@Component(modules = {AppModule.class, FirebaseAuthModule.class})
public interface AppComponent {

    void inject(LoginActivity loginActivity);

    void inject(SignUpActivity signUpActivity);
}
