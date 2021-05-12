package com.omartaha.mocknewsapp.onBoardingScreens.viewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.omartaha.mocknewsapp.R;
import com.omartaha.mocknewsapp.onBoardingScreens.screens.FirstScreenFragment;
import com.omartaha.mocknewsapp.onBoardingScreens.screens.SecondScreenFragment;
import com.omartaha.mocknewsapp.onBoardingScreens.screens.ThirdScreenFragment;

import java.util.ArrayList;

public class ViewPagerFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_pager, container, false);
        ArrayList<Fragment> fragmentArrayList = new ArrayList<>();

        fragmentArrayList.add(new FirstScreenFragment());
        fragmentArrayList.add(new SecondScreenFragment());
        fragmentArrayList.add(new ThirdScreenFragment());

        ViewPagerAdapter adapter = new ViewPagerAdapter(fragmentArrayList,
                requireActivity().getSupportFragmentManager(),
                getLifecycle());
        ViewPager2 viewPager2 = view.findViewById(R.id.viewPager2);
        viewPager2.setAdapter(adapter);

        return view;
    }
}