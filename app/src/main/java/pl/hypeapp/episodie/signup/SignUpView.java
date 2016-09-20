package pl.hypeapp.episodie.signup;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;

import net.grandcentrix.thirtyinch.TiView;

interface SignUpView extends TiView {

    FirebaseAuth getFirebaseAuth();

    void onCompleteSignUp();

    void onFailureSignUp();

    void onSuccessSignUp();

}
