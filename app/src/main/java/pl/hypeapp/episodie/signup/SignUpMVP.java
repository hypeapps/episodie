package pl.hypeapp.episodie.signup;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.tinmegali.mvp.mvp.ModelOps;
import com.tinmegali.mvp.mvp.PresenterOps;

import pl.hypeapp.episodie.base.BaseActivityView;

public interface SignUpMVP {
    /**
     * Required VIEW methods available to PRESENTER
     *      PRESENTER to VIEW
     */
    interface RequiredViewOps extends BaseActivityView {
        Activity getActivity();
        FirebaseAuth getFirebaseAuth();
        void onCompleteSignUp();
        void onFailureSignUp();
        void onSuccessSignUp();
    }


    /**
     * Operations offered to VIEW to communicate with PRESENTER
     *      VIEW to PRESENTER
     */
    interface ProvidedPresenterOps extends PresenterOps<RequiredViewOps> {
        void loadImageFromResourcesIntoView(ImageView view, String path);
        void registerUser(String email, String password);
    }

    /**
     * Required PRESENTER methods available to MODEL
     *      MODEL to PRESENTER
     */
    interface RequiredPresenterOps {
    }

    /**
     * Operations offered to MODEL to communicate with PRESENTER
     *      PRESENTER to MODEL
     */
    interface ProvidedModelOps extends ModelOps<RequiredPresenterOps> {
        DrawableTypeRequest getBitmapFromUrl(String url, Context context);
    }

}
