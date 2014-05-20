package com.qdu.jw.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.qdu.jw.app.fragment.TabNavigationPagerFragment;

/**
 * Created by wang on 5/20/14.
 */

public class TabNavigationPagerAdapter extends FragmentPagerAdapter{
    private final static int TAB_NUM = 4;

    public TabNavigationPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return TabNavigationPagerFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        return TAB_NUM;
    }
}
