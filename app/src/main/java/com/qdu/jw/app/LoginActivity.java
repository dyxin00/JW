package com.qdu.jw.app;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

import com.qdu.jw.app.fragment.LoginFragment;


public class LoginActivity extends ActionBarActivity {

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        addLoginFragment(savedInstanceState);
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
