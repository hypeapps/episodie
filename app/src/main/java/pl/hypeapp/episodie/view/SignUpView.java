package pl.hypeapp.episodie.view;

import com.google.firebase.auth.FirebaseAuth;

import net.grandcentrix.thirtyinch.TiView;

public interface SignUpView extends TiView {

    FirebaseAuth getFirebaseAuth();

    void onCompleteSignUp();

    void onFailureSignUp();

    void onSuccessSignUp();

}
