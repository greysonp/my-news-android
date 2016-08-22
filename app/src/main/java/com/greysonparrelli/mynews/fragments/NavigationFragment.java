package com.greysonparrelli.mynews.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.greysonparrelli.mynews.R;

/**
 * @author Greyson Parrelli (keybase.io/greyson)
 */
public class NavigationFragment extends Fragment {

    public static final String TAG = "NavigationFragment";

    public static NavigationFragment newInstance() {
        return new NavigationFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_navigation, container, false);
    }
}
