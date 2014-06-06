package com.qdu.jw.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qdu.jw.app.R;

/**
 * Created by wang on 6/5/14.
 */
public class ScheduleFragment extends Fragment{

    String name;
    public ScheduleFragment(String name){
        this.name = name;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_schedule, container, false);
        ((TextView)rootView.findViewById(R.id.text)).setText(name);
        return rootView;
    }
}
