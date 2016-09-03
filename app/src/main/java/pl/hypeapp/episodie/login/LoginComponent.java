package pl.hypeapp.episodie.login;

import javax.inject.Singleton;

import dagger.Component;
import pl.hypeapp.episodie.AppModule;
import pl.hypeapp.episodie.network.firebase.FirebaseAuthModule;

@Singleton
@Component(modules = {AppModule.class, FirebaseAuthModule.class})
public interface LoginComponent {

    void inject(LoginActivity loginActivity);
}
