package com.omartaha.mocknewsapp.others;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.omartaha.mocknewsapp.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);
    }
}