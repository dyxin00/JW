package com.qdu.jw.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.qdu.jw.app.fragment.ScheduleFragment;
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
        switch (position){
            case 0:
                ScheduleFragment scheduleFragment = new ScheduleFragment("schedule1");
                return  scheduleFragment;
            case 1:
                ScheduleFragment scheduleFragment1 = new ScheduleFragment("schedule2");
                return  scheduleFragment1;
            case 2:
                ScheduleFragment scheduleFragment2 = new ScheduleFragment("schedule3");
                return  scheduleFragment2;
            case 3:
                ScheduleFragment scheduleFragment3 = new ScheduleFragment("schedule4");
                return  scheduleFragment3;
        }
        return null;
    }

    @Override
    public int getCount() {
        return TAB_NUM;
    }
}
