package com.omartaha.mocknewsapp.others;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.omartaha.mocknewsapp.MainActivity;
import com.omartaha.mocknewsapp.R;

public class SplashFragment extends Fragment {
    final static int SPLASH_TIME = 5000;
    Animation topAnim, bottomAnim;
    ImageView splashImage;
    TextView firstSplashText, secondSplashText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_splash, container, false);
        topAnim = AnimationUtils.loadAnimation(getContext(), R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(getContext(), R.anim.bottom_animation);
        splashImage = view.findViewById(R.id.splash_image);
        firstSplashText = view.findViewById(R.id.first_splash_text);
        secondSplashText = view.findViewById(R.id.second_splash_text);
        splashImage.setAnimation(topAnim);
        firstSplashText.setAnimation(bottomAnim);
        secondSplashText.setAnimation(bottomAnim);

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        if (onBoardingFinished()) {
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        } else {
                            Navigation.findNavController(view).navigate(R.id.splash_to_viewPager);
                        }
                    }
                }, SPLASH_TIME);

        return view;
    }

    private boolean onBoardingFinished() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences(getString(R.string.on_boarding), Context.MODE_PRIVATE);
        return sharedPref.getBoolean(getString(R.string.finish), false);
    }
}