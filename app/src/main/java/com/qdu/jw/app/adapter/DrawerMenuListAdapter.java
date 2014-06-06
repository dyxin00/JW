package com.qdu.jw.app.adapter;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.qdu.jw.app.IndexActivity;
import com.qdu.jw.app.R;
import com.qdu.jw.app.models.User;
import com.qdu.jw.app.utils.DatabaseHelper;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by wang on 5/27/14.
 */
public class DrawerMenuListAdapter extends ArrayAdapter<String> {

    public DrawerMenuListAdapter(Context context, int resource, String[] objects){
        super(context, resource, objects);
    }

    public String[] getMenuItemTitle(){
        return getContext().getResources().getStringArray(R.id.left_drawer_menu);
    }
    public static DrawerMenuListAdapter newAdapter(Context context, DatabaseHelper helper, String[] menuItemTitles){
        try {
            Dao<User, Integer> userDao = helper.getUserDao();
            User user = new User();
            user.setLoginStatus(true);
            List<User> list = userDao.queryForMatching(user);
            if(list.size() == 1){
                menuItemTitles[0] = list.get(0).getFullName();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Log.i("wang", "-->" + menuItemTitles[0]);
        DrawerMenuListAdapter adapter = new DrawerMenuListAdapter(context, R.layout.drawer_menu_list_item, menuItemTitles);
        return adapter;
    }

}
