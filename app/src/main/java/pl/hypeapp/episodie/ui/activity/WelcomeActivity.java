package pl.hypeapp.episodie.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.merhold.extensiblepageindicator.ExtensiblePageIndicator;
import com.pascalwelsch.compositeandroid.activity.CompositeActivity;

import net.grandcentrix.thirtyinch.internal.TiPresenterProvider;
import net.grandcentrix.thirtyinch.plugin.TiActivityPlugin;

import pl.hypeapp.episodie.R;
import pl.hypeapp.episodie.adapter.WelcomePagerAdapter;
import pl.hypeapp.episodie.presenter.SplashScreenPresenter;
import pl.hypeapp.episodie.presenter.WelcomePresenter;
import pl.hypeapp.episodie.util.animation.ParallaxPageTransformer;
import pl.hypeapp.episodie.view.WelcomeView;

public class WelcomeActivity extends CompositeActivity implements WelcomeView {

    private final TiActivityPlugin<WelcomePresenter, WelcomeView> mPresenterPlugin =
            new TiActivityPlugin<>(new TiPresenterProvider<WelcomePresenter>() {
                @NonNull
                @Override
                public WelcomePresenter providePresenter() {
                    return new WelcomePresenter();
                }
            });

    private SplashScreenPresenter mPresenter;
    private ViewPager mViewPager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mViewPager = (ViewPager) findViewById(R.id.pager_welcome);
        initViewPager(mViewPager);
    }

    void initViewPager(ViewPager viewPager) {
        WelcomePagerAdapter welcomePagerAdapter = new WelcomePagerAdapter(getSupportFragmentManager());
        viewPager.setPageTransformer(true, new ParallaxPageTransformer());
        viewPager.setAdapter(welcomePagerAdapter);
        ExtensiblePageIndicator extensiblePageIndicator = (ExtensiblePageIndicator) findViewById(R.id.page_indicator);
        extensiblePageIndicator.initViewPager(mViewPager);
    }

}
