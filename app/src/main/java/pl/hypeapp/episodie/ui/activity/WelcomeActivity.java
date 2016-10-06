package pl.hypeapp.episodie.ui.activity;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import com.merhold.extensiblepageindicator.ExtensiblePageIndicator;
import com.pascalwelsch.compositeandroid.activity.CompositeActivity;

import net.grandcentrix.thirtyinch.internal.TiPresenterProvider;
import net.grandcentrix.thirtyinch.plugin.TiActivityPlugin;

import pl.hypeapp.episodie.R;
import pl.hypeapp.episodie.adapter.WelcomePagerAdapter;
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

    public WelcomeActivity(){
        addPlugin(mPresenterPlugin);
    }
    private WelcomePresenter mPresenter;
    private ViewPager mViewPager;

    private View.OnClickListener onNextListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onNextPage();
            }
        };
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mViewPager = (ViewPager) findViewById(R.id.pager_welcome);
        initViewPager(mViewPager);
        mPresenter = mPresenterPlugin.getPresenter();
        ImageView nextPage = (ImageView) findViewById(R.id.iv_next);
        nextPage.setOnClickListener(onNextListener());
    }

    @Override
    public void animatePagerTransition(final boolean forward) {
        ValueAnimator animator = ValueAnimator.ofInt(0,
                mViewPager.getWidth() - ( forward ? mViewPager.getPaddingLeft() : mViewPager.getPaddingRight() ));
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mViewPager.endFakeDrag();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mViewPager.endFakeDrag();
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        animator.setInterpolator(new AccelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            int oldDragPosition = 0;
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int dragPosition = (Integer) animation.getAnimatedValue();
                int dragOffset = dragPosition - oldDragPosition;
                oldDragPosition = dragPosition;
                mViewPager.fakeDragBy(dragOffset * (forward ? -1 : 1));
            }
        });
        animator.setDuration(400);
        mViewPager.beginFakeDrag();
        animator.start();
    }

    private void initViewPager(ViewPager viewPager) {
        WelcomePagerAdapter welcomePagerAdapter = new WelcomePagerAdapter(getSupportFragmentManager());
        viewPager.setPageTransformer(true, new ParallaxPageTransformer());
        viewPager.setAdapter(welcomePagerAdapter);
        ExtensiblePageIndicator extensiblePageIndicator = (ExtensiblePageIndicator) findViewById(R.id.page_indicator);
        extensiblePageIndicator.initViewPager(mViewPager);
    }
}
