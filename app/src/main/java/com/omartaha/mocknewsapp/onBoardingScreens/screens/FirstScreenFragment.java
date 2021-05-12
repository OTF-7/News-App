package com.omartaha.mocknewsapp.onBoardingScreens.screens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.button.MaterialButton;
import com.omartaha.mocknewsapp.R;

public class FirstScreenFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first_screen, container, false);
        ViewPager2 viewPager2 = getActivity().findViewById(R.id.viewPager2);
        MaterialButton button = view.findViewById(R.id.onBoarding_next_btn_1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager2.setCurrentItem(1, true);
            }
        });
        return view;
    }
}