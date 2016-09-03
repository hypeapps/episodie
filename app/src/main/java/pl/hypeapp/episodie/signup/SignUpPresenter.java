package pl.hypeapp.episodie.signup;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.tinmegali.mvp.mvp.GenericPresenter;

import pl.hypeapp.episodie.util.image.BlurTransformation;
import pl.hypeapp.episodie.util.image.GrayscaleTransformation;

public class SignUpPresenter extends GenericPresenter<SignUpMVP.RequiredPresenterOps, SignUpMVP.ProvidedModelOps,
        SignUpMVP.RequiredViewOps, SignUpModel>
        implements
        SignUpMVP.RequiredPresenterOps,
        SignUpMVP.ProvidedPresenterOps {

    SignUpActivity activity;

    @Override
    public void onCreate(SignUpMVP.RequiredViewOps view) {
        super.onCreate(SignUpModel.class, this);
        setView(view);
        activity = (SignUpActivity) getView().getActivity();
    }

    public void loadImageFromUrlIntoView(ImageView view, String url) {
        getModel().getBitmapFromUrl(url, activity)
                .bitmapTransform(new BlurTransformation(activity, 12), new GrayscaleTransformation(activity))
                .into(view);
    }

    @Override
    public void registerUser(String email, String password) {
        activity.getFirebaseAuth().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(getView()!= null) {
                            getView().onCompleteSignUp();
                        }
                    }
                })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(getView()!= null) {
                    getView().onFailureSignUp();
                }
                Log.e("LOGIN_FAILURE", e.getMessage());
            }
        })
        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                if(getView()!= null) {
                    getView().onSuccessSignUp();
                }

                Log.e("LOGIN_SUCCESS", " " + authResult.getUser().getEmail());

            }
        });
    }

    @Override
    public void onConfigurationChanged(SignUpMVP.RequiredViewOps view) {
        setView(view);
        activity = (SignUpActivity) getView().getActivity();
    }

    @Override
    public void onDestroy(boolean isChangingConfiguration) {}

    @Override
    public void onBackPressed() {}
}
