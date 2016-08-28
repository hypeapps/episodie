package pl.hypeapp.episoder.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.hypeapp.episoder.R;
import pl.hypeapp.episoder.animation.BlurTransformation;
import pl.hypeapp.episoder.animation.GrayscaleTransformation;


public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.iv_login_background)
    ImageView loginBackground;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Glide.with(this).load("http://www.goliath.com/wp-content/uploads/2015/12/1035x776-breakingbad-1800-1404309469.jpg")
                .bitmapTransform(new BlurTransformation(this, 12), new GrayscaleTransformation(this))
                .into(loginBackground);
    }
}
