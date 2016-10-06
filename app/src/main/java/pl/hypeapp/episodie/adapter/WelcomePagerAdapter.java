package pl.hypeapp.episodie.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import pl.hypeapp.episodie.ui.fragment.RegistrationMethodFragment;
import pl.hypeapp.episodie.ui.fragment.factory.WelcomeFragmentFactory;

public class WelcomePagerAdapter extends FragmentPagerAdapter {

    public WelcomePagerAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(isRegistrationMethodFragmentPosition(position)){
            return new RegistrationMethodFragment();
        }
        return WelcomeFragmentFactory.newInstance(position);
    }

    private boolean isRegistrationMethodFragmentPosition(int position) {
        return position == 2;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
