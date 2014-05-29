package com.qdu.jw.app;

import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.qdu.jw.app.adapter.DrawerMenuListAdapter;
import com.qdu.jw.app.adapter.TabNavigationPagerAdapter;
import com.qdu.jw.app.models.User;
import com.qdu.jw.app.utils.DatabaseHelper;

import java.sql.SQLException;
import java.util.List;


public class IndexActivity extends ActionBarActivity {
    private ActionBar mActionBar;
    private ViewPager mViewPager;
    private TabNavigationPagerAdapter mPagerAdapter;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerMenuList;
    private ActionBarDrawerToggle mDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        initActionBar();
        initViewPager();
        initNavigationTab();
        initDrawerMenu();
        try {
            Dao<User, Integer> userDao = getHelper().getUserDao();
            User user = new User();
            user.setStudentId("201140705021");
            List<User> list = userDao.queryForMatching(user);
            Log.i("wang", "user login status after login" + list.get(0).getLoginStatus());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.index, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch(item.getItemId()){
            case R.id.action_settings:
                //TODO:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void initActionBar(){
        mActionBar = getSupportActionBar();
//        mActionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void initNavigationTab(){
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        mActionBar.setDisplayShowTitleEnabled(true);
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

            }
        };
        String[] tabTitle = getResources().getStringArray(R.array.tab_title);
        for(int i = 0; i < mPagerAdapter.getCount(); i++){
            mActionBar.addTab(mActionBar.newTab().setText(tabTitle[i]).setTabListener(tabListener));
        }
    }

    public void initViewPager(){
        mViewPager = (ViewPager)findViewById(R.id.tab_view_pager);
        mPagerAdapter = new TabNavigationPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mActionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public void initDrawerMenu(){
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close){
            public void onDrawerClosed(View view){
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(R.string.drawer_close);
                invalidateOptionsMenu();//create call to onPreparedOptionsMenu()
            }

            public void onDrawerOpened(View view){
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(R.string.drawer_open);
                invalidateOptionsMenu();

            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerMenuList = (ListView)findViewById(R.id.left_drawer_menu);
        String[] menuItemTitle = getResources().getStringArray(R.array.drawer_menu_item_title);

        //TODO:create adapter of menu list
        mDrawerMenuList.setAdapter(DrawerMenuListAdapter.newAdapter(this, getHelper(), menuItemTitle));
        mDrawerMenuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO:create item click event
            }
        });
        mActionBar.setHomeButtonEnabled(true);
    }

    /*called whenever we call invalidateOptionsMenu()*/
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private DatabaseHelper mDatabaseHelper = null;
    private DatabaseHelper getHelper(){
        if(mDatabaseHelper == null){
            mDatabaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return mDatabaseHelper;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mDatabaseHelper != null){
            OpenHelperManager.releaseHelper();
            mDatabaseHelper = null;
        }
    }
}
