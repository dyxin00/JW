package com.qdu.jw.app.fragment;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.qdu.jw.app.R;
import com.qdu.jw.app.models.User;
import com.qdu.jw.app.utils.DatabaseHelper;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by wang on 5/29/14.
 */
public class UserInfoFragment extends Fragment{
    private TextView mStudentId;
    private TextView mFullName;
    private TextView mCollege;
    private TextView mSchoolYear;
    private TextView mTeam;
    private TextView mCourse;
    private TextView mSpecialty;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_userinfo, container, false);
        initFragment(rootView);
        setUserInfo();
//        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        return rootView;
    }

    public void initFragment(View rootView){
        mStudentId = (TextView)rootView.findViewById(R.id.text_view_student_id);
        mFullName = (TextView)rootView.findViewById(R.id.text_view_full_name);
        mCollege = (TextView)rootView.findViewById(R.id.text_view_college);
        mSchoolYear = (TextView)rootView.findViewById(R.id.text_view_school_year);
        mTeam = (TextView)rootView.findViewById(R.id.text_view_team);
        mCourse = (TextView)rootView.findViewById(R.id.text_view_course);
        mSpecialty = (TextView)rootView.findViewById(R.id.text_view_specialty);
    }

    public void setUserInfo(){
        try {
            Dao<User, Integer> userDao = getHelper().getUserDao();
            User user = new User();
            user.setLoginStatus(true);
            List<User> list = userDao.queryForMatching(user);
            if(list.size() == 1){
                User user1 = list.get(0);
                mStudentId.setText(user1.getStudentId());
                mFullName.setText(user1.getFullName());
                mCollege.setText(user1.getCollege());
                mSchoolYear.setText(user1.getSchoolYear());
                mTeam.setText(user1.getTeam());
                mCourse.setText(user1.getCourse());
                mSpecialty.setText(user1.getSpecialty());
            }
            else{
                //TODO:if the query result is none
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private DatabaseHelper mDatabaseHelper = null;
    private DatabaseHelper getHelper(){
        if(mDatabaseHelper == null){
            mDatabaseHelper = OpenHelperManager.getHelper(getActivity(), DatabaseHelper.class);
        }
        return mDatabaseHelper;
    }
}
