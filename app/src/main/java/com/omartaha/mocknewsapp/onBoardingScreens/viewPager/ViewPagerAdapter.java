package com.omartaha.mocknewsapp.onBoardingScreens.viewPager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentStateAdapter {

    private final ArrayList<Fragment> fragmentArrayList;

    public ViewPagerAdapter(ArrayList<Fragment> list,
                            FragmentManager fragmentManager,
                            Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
        fragmentArrayList = list;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentArrayList.size();
    }
}
