package com.bluewhaleyt.whaleutils.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.bluewhaleyt.whaleutils.fragments.FileManagementFragment;
import com.bluewhaleyt.whaleutils.fragments.GitFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private static final int TAB_COUNT = 2;

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new GitFragment();
            case 1: return new FileManagementFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: return "Git";
            case 1: return "File Management";
        }
        return null;
    }
}
