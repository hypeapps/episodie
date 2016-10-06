package pl.hypeapp.episodie.ui.fragment.factory;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.hypeapp.episodie.R;

public class WelcomeFragmentFactory extends Fragment {

    public static WelcomeFragmentFactory newInstance(int position) {
        Bundle args = new Bundle();
        WelcomeFragmentFactory fragment = new WelcomeFragmentFactory();
        args.putInt("fragment_welcome_position", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("resource ", "" + getArguments().getInt("fragment_welcome_position", 0) );
        int layoutResource = getProperIdLayout(getArguments().getInt("fragment_welcome_position", 0));
        View v = inflater.inflate(layoutResource, container, false);
        return v;
    }

    private int getProperIdLayout(int position){
        switch(position){
            case 0:
                return R.layout.fragment_welcome_about;
            case 1:
                return R.layout.fragment_welcome_registration_method;
//            case 2:
//                return R.layout.htu_fragment3;
//            case 3:
//                return R.layout.htu_fragment4;
        }
        return 0;
    }
}