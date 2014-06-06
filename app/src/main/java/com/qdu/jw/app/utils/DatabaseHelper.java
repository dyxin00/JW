package com.qdu.jw.app.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.qdu.jw.app.models.User;

import java.sql.SQLException;

/**
 * Created by wang on 5/27/14.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper{
    private static final String DATABASE_NAME = "jw.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<User, Integer> userDao = null;

    //create a constructor with single argument(context), that can solve the problem of getHelper() raise below exception
    //java.lang.IllegalStateException: Could not find public constructor that has a single (Context) argument for helper class class com.qdu.jw.app.utils.DatabaseHelper
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, User.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }

    @Override
    public void close() {
        super.close();
        userDao = null;
    }

    public Dao<User, Integer> getUserDao() throws SQLException {
        if(userDao == null)
            userDao = getDao(User.class);
        return userDao;
    }
}
