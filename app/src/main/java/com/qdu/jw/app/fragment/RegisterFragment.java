package com.qdu.jw.app.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.qdu.jw.app.LoginActivity;
import com.qdu.jw.app.R;
import com.qdu.jw.app.linkServer.CreateAsyncHttpClient;
import com.qdu.jw.app.models.User;
import com.qdu.jw.app.utils.DatabaseHelper;

import org.apache.http.Header;
import org.json.JSONArray;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by wang  and xin on 5/22/14.
 */

public class RegisterFragment extends Fragment{
    private EditText accountEditText;
    private EditText jwPassCodeEditText;
    private EditText clientPassCodeEditText;
    private EditText captchaEditText;
    private ImageView imageViewCaptcha;
    private Button signIn;
    private Button refresh;
    private DatabaseHelper databaseHelper = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);
        setHomeAsUpEnable();
        initViewWidget(rootView);
        ExecutionEventView(rootView, container);
        getHelper();
        return rootView;
    }
    public void setHomeAsUpEnable(){
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void ExecutionEventView(View rootView, ViewGroup container){

        registerCaptcha();
        signIn.setOnClickListener(new RegisterOnClickListener());
        refresh.setOnClickListener(new CaptchaOnClickListener());
        imageViewCaptcha.setOnClickListener(new CaptchaOnClickListener());
    }

    private void initViewWidget(View rootView){

        accountEditText = (EditText) rootView.findViewById(R.id.edit_text_register_account);
        jwPassCodeEditText = (EditText) rootView.findViewById(R.id.edit_text_register_jwpassword);
        clientPassCodeEditText = (EditText) rootView.findViewById(R.id.edit_text_register_password);
        captchaEditText = (EditText) rootView.findViewById(R.id.edit_text_register_captcha);
        imageViewCaptcha = (ImageView) rootView.findViewById(R.id.image_view_register_captcha);
        signIn = (Button) rootView.findViewById(R.id.button_register_login);
        refresh = (Button) rootView.findViewById(R.id.button_register_refresh_captcha);
    }

    private void registerCaptcha(){

        AsyncHttpClient client = CreateAsyncHttpClient.getClient();
        String Url = getString(R.string.URL) + "/captcha";

        client.get(Url, null, new AsyncHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(responseBody, 0, responseBody.length);
                imageViewCaptcha.setImageBitmap(bitmap);
                Log.i("wang", "set captcha succeed");
            }

            @Override
            public void onStart() {
                super.onStart();
                Log.i("xin", "register captcha");
            }
        });
    }


    class CaptchaOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            registerCaptcha();
        }
    }


    class RegisterOnClickListener implements View.OnClickListener {

        private AsyncHttpClient client;
        RequestParams params;
        String Url = getString(R.string.URL) + "/registration";
        String account;
        String jwPassCode;
        String clientPassCode;
        String captcha;

        @Override
        public void onClick(View v) {

            if(accessContent()) {

                client = CreateAsyncHttpClient.getClient();
                params = new RequestParams();
                params.put("s_id", account);
                params.put("s_passcode", jwPassCode);
                params.put("captcha", captcha);
                params.put("c_passcode", clientPassCode);
                client.post(Url, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onStart() {
                        Log.i("xinLog", "zhucestart");
                    }

                    @Override
                    public void onSuccess(String content) {
                        JSONObject jsonObject = JSON.parseObject(content);
                        int status = jsonObject.getInteger("status");
                        String Url = getString(R.string.URL) + "/student_info";
                        switch (status) {

                            case 200: {
                                client.get(Url, null, new AsyncHttpResponseHandler() {
                                    @Override
                                    public void onSuccess(String content) {
                                        //Log.i("xin", content);
                                        List<JSONObject> jsonObjects = JSON.parseArray(content, JSONObject.class);
                                        JSONObject info = jsonObjects.get(0);
//                                        for (JSONObject i : jsonObjects) {
//                                            Log.i("xin", i.getString("student_id") + i.getString("week_start") + "  " + i.getString("week") + "   " + i.getString("day_start"));
                                            Log.i("wang", info.getString("student_id") + " " + info.getString("full_name")
                                            + " " + info.getString("college")
                                            + " " + info.getString("school_year")
                                            + " " + info.getString("team")
                                            + " " + info.getString("specialty"));
//                                        }
                                        Log.i("wang", "student info succeed!");
                                        Dao<User, Integer> userDao = null;
                                        try {
                                            userDao = getHelper().getUserDao();
                                            User user = new User();
                                            user.setStudentId(info.getString("student_id"));
                                            user.setFullName(info.getString("full_name"));
                                            user.setCollege(info.getString("college"));
                                            user.setSchoolYear(info.getString("school_year"));
                                            user.setTeam(info.getString("team"));
                                            user.setSpecialty(info.getString("specialty"));
                                            user.setCourse(info.getString("course"));

                                            userDao.create(user);
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                break;
                            }
                            case 210: {
                                // The user already exists
                                Toast.makeText(getActivity(), getString(R.string.account_exists), Toast.LENGTH_SHORT).show();
                                break;
                            }
                            case 601: {
                                // jw account is  error
                                Toast.makeText(getActivity(), getString(R.string.account_error), Toast.LENGTH_SHORT).show();
                                break;
                            }
                            case 602: {
                                // jw password is  error
                                Toast.makeText(getActivity(), getString(R.string.jw_password_error), Toast.LENGTH_SHORT).show();
                                break;
                            }
                            case 603: {
                                // jw captcha is  error
                                Toast.makeText(getActivity(), getString(R.string.captcha_error), Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }
                        Log.i("xinLog", String.valueOf(status));
                        System.out.println(status);
                    }
                });
            }

        }

        private boolean accessContent(){

            account = String.valueOf(accountEditText.getText());
            if(account.isEmpty()){
                Toast.makeText(getActivity(), getString(R.string.account_null), Toast.LENGTH_SHORT).show();
                return false;
            }
            jwPassCode = String.valueOf(jwPassCodeEditText.getText());
            if(jwPassCode.isEmpty()){

                Toast.makeText(getActivity(), getString(R.string.jw_password_null), Toast.LENGTH_SHORT).show();
                return false;
            }
            clientPassCode = String.valueOf(clientPassCodeEditText.getText());
            if(clientPassCode.isEmpty()){

                Toast.makeText(getActivity(), getString(R.string.client_password_null), Toast.LENGTH_SHORT).show();
                return false;
            }
            captcha = String.valueOf(captchaEditText.getText());
            if(captcha.isEmpty()){

                Toast.makeText(getActivity(), getString(R.string.captcha_null), Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if(databaseHelper != null){
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }

    private DatabaseHelper getHelper(){
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(getActivity(), DatabaseHelper.class);
        }
        return databaseHelper;
    }
}
