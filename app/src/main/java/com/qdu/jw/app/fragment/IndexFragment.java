package com.qdu.jw.app.fragment;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.qdu.jw.app.R;
import com.qdu.jw.app.adapter.DrawerMenuListAdapter;
import com.qdu.jw.app.adapter.TabNavigationPagerAdapter;
import com.qdu.jw.app.utils.DatabaseHelper;

import java.util.List;

/**
 * Created by wang on 6/3/14.
 */
public class IndexFragment extends Fragment {
    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    private ActionBar mActionBar;
    private static boolean hasAdd = false;

    /*
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*Your fragments can contribute menu items to the activity's Options Menu
        (and, consequently, the Action Bar) by implementing onCreateOptionsMenu().
        In order for this method to receive calls, however,
        you must call setHasOptionsMenu() during onCreate(),
        to indicate that the fragment would like to add items to the Options Menu
        (otherwise, the fragment will not receive a call to onCreateOptionsMenu()).
        setHasOptionsMenu(true);
    }*/

    @Override
    public void onPause() {
        super.onPause();
        Log.i("wang", "onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        ActionBar.Tab tab = mActionBar.getTabAt(0);
        mActionBar.selectTab(tab);
        Log.i("wang", "onResume");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("wang", "create view");
        View rootView = inflater.inflate(R.layout.fragment_index, container, false);
        initWidget(rootView);
        initViewPager();
        initNavigationTab();
        return rootView;
    }

    public void initWidget(View rootView) {
        mActionBar = getActivity().getActionBar();
        mPagerAdapter = new TabNavigationPagerAdapter(getActivity().getSupportFragmentManager());
        mViewPager = (ViewPager) rootView.findViewById(R.id.tab_view_pager);
    }

    public void initViewPager() {
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.i("wang", "indexfragment onPageSelected");
                mActionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    public void initNavigationTab() {
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        mActionBar.setDisplayShowTitleEnabled(true);
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {
                Log.i("wang", "fragment-->select a tab" + " " + tab.getPosition());
                mViewPager.setCurrentItem(tab.getPosition());
//                mActionBar.setSelectedNavigationItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {

            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {

            }
        };
        if (hasAdd == false) {
            String[] tabTitle = getResources().getStringArray(R.array.tab_title);
            for (int i = 0; i < mPagerAdapter.getCount(); i++) {
                mActionBar.addTab(mActionBar.newTab().setText(tabTitle[i]).setTabListener(tabListener));
            }
            hasAdd = true;
        }
        else{
            int count = mActionBar.getTabCount();
            for(int i = 0; i < count; i++){
                mActionBar.getTabAt(i).setTabListener(tabListener);
            }
        }
    }
}
