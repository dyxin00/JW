package com.qdu.jw.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qdu.jw.app.R;

/**
 * Created by wang on 5/20/14.
 */
public class TabNavigationPagerFragment extends Fragment{
    public final static String KEY_STRING = "section_number";
    public TabNavigationPagerFragment(){

    }

    public static TabNavigationPagerFragment newInstance(int position){
        TabNavigationPagerFragment fragment = new TabNavigationPagerFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_STRING, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab_navigation_pager, container, false);
        Bundle args = getArguments();
        ((TextView)rootView.findViewById(R.id.pager_text_view)).setText("hello" + args.getInt(KEY_STRING));
        return rootView;
    }
}
