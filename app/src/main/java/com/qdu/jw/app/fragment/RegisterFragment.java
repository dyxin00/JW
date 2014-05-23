package com.qdu.jw.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qdu.jw.app.R;

/**
 * Created by wang on 5/22/14.
 */

public class RegisterFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);
        setHomeAsUpEnable();
        return rootView;
    }
    public void setHomeAsUpEnable(){
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
