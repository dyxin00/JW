package com.qdu.jw.app;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.qdu.jw.app.fragment.LoginFragment;
import com.qdu.jw.app.linkServer.CreateAsyncHttpClient;
import com.qdu.jw.app.models.User;
import com.qdu.jw.app.utils.DatabaseHelper;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;


public class LoginActivity extends ActionBarActivity {

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        try {
            Dao<User, Integer> userDao = getHelper().getUserDao();
            User user = new User();
//            user.setStudentId("201140705021");
//            List<User> list = userDao.queryForMatching(user);
            List<User> list = userDao.queryForAll();
            Log.i("wang", "user " + list.size() + " " + list.get(0).getLoginStatus() + " " + list.get(0).getStudentId() + " " + list.get(0).getFullName() + " " + list.get(0).getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }


        CreateAsyncHttpClient.createClient(getBaseContext());
        addLoginFragment(savedInstanceState);
    }
    private DatabaseHelper databaseHelper = null;
    public DatabaseHelper getHelper(){
        if(databaseHelper == null)
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        return databaseHelper;
    }

    public void addLoginFragment(final Bundle savedInstanceState) {
        mFragmentManager = getSupportFragmentManager();
        //Check the activity is using layout version with fragment_container layout
        if (findViewById(R.id.fragment_container_login) != null) {
            //However, if we're being restored from previous state,
            //then we don't need to do anything and should return or else
            //we could end up with overlapping fragments
            if (savedInstanceState != null) {
                return;
            }
            //Create a new Fragment to be placed into activity layout
            LoginFragment loginFragment = new LoginFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            FragmentTransaction transaction = mFragmentManager.beginTransaction();

            transaction.replace(R.id.fragment_container_login, loginFragment);
            transaction.commit();
        }
    }


    //override this method, to navigate back from fragments
    @Override
    public boolean onSupportNavigateUp() {
        mFragmentManager.popBackStack();
        return true;
    }
}
