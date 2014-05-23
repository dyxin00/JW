package com.qdu.jw.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.qdu.jw.app.R;

/**
 * Created by wang on 5/22/14.
 */
public class LoginFragment extends Fragment{
    private Button mRegisterButton;
    private FragmentManager mFragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        initLoginFragment(rootView);
        return rootView;
    }

    public void initLoginFragment(View rootView){
        setHomeAsUpDisable();
        mFragmentManager = getFragmentManager();
        mRegisterButton = (Button)(rootView).findViewById(R.id.button_register);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = mFragmentManager.beginTransaction();
                RegisterFragment registerFragment = new RegisterFragment();
                transaction.replace(R.id.fragment_container_login, registerFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }
    public void setHomeAsUpDisable(){
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(false);
    }

}
