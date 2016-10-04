package pl.hypeapp.episodie.ui.fragment.factory;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class WelcomeFragmentFactory extends Fragment {

    public static WelcomeFragmentFactory newInstance(int position) {
        Bundle args = new Bundle();
        WelcomeFragmentFactory fragment = new WelcomeFragmentFactory();
        args.putInt("htu_fragment", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int layoutResource = getProperIdLayout(getArguments().getInt("htu_fragment", 0));
        View v = inflater.inflate(layoutResource, container, false);
        return v;
    }

    int getProperIdLayout(int position){
        switch(position){
//            case 0:
//                return R.layout.htu_fragment1;
//            case 1:
//                return R.layout.htu_fragment2;
//            case 2:
//                return R.layout.htu_fragment3;
//            case 3:
//                return R.layout.htu_fragment4;
        }
        return 0;
    }
}