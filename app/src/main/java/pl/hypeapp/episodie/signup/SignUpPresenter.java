package pl.hypeapp.episodie.signup;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import net.grandcentrix.thirtyinch.TiPresenter;

public class SignUpPresenter extends TiPresenter<SignUpView> {

    SignUpActivity activity;

    public void registerUser(String email, String password) {
        activity.getFirebaseAuth().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (getView() != null) {
                            getView().onCompleteSignUp();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (getView() != null) {
                            getView().onFailureSignUp();
                        }
                        Log.e("LOGIN_FAILURE", e.getMessage());
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        if (getView() != null) {
                            getView().onSuccessSignUp();
                        }

                        Log.e("LOGIN_SUCCESS", " " + authResult.getUser().getEmail());

                    }
                });
    }
}
