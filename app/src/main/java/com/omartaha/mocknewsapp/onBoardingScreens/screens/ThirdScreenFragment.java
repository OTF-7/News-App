package com.omartaha.mocknewsapp.onBoardingScreens.screens;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.omartaha.mocknewsapp.MainActivity;
import com.omartaha.mocknewsapp.R;

public class ThirdScreenFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_third_screen, container, false);
        MaterialButton finish = view.findViewById(R.id.onBoarding_finish_btn);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
                onBoardingFinished();
                getActivity().finish();
            }
        });

        return view;
    }

    private void onBoardingFinished() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences(getString(R.string.on_boarding), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(getString(R.string.finish), true);
        editor.apply();
    }

}