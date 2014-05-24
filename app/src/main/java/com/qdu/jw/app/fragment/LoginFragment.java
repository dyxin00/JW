package com.qdu.jw.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.qdu.jw.app.R;

import com.qdu.jw.app.linkServer.CreateAsyncHttpClient;

import java.util.List;

/**
 * Created by wang and xin on 5/22/14.
 */
public class LoginFragment extends Fragment{
    private Button mRegisterButton;
    private FragmentManager mFragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        initLoginFragment(rootView);

        initViewWidget(rootView);
        ExecutionEventView(rootView, container);

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

    private void ExecutionEventView(View rootView, ViewGroup container){

        final boolean boolCheckBox = false;

        button.setOnClickListener(new LoginOnClickListener());

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
    }
    class LoginOnClickListener implements View.OnClickListener{

        final String Url = getString(R.string.URL) + "/login_client";
        String account;
        String passCode;
        @Override
        public void onClick(View v) {

            final AsyncHttpClient client = CreateAsyncHttpClient.getClient();
            RequestParams params = new RequestParams();
            if(accessContent()) {

                params.put("c_id", account);
                params.put("c_passcode", passCode);

                client.post(Url, params, new AsyncHttpResponseHandler() {

                    @Override
                    public void onStart() {
                        Log.i("xinLog", "start login jw");
                    }

                    @Override
                    public void onSuccess(String content) {

                        JSONObject jsonObject = JSON.parseObject(content);
                        int status = jsonObject.getInteger("status");
                        Log.i("xinLog", String.valueOf(status));
                        String Url = getString(R.string.URL) + "/get_lesson";
                        switch (status) {
                            case 200: {
                                client.get(Url, null,
                                        new AsyncHttpResponseHandler() {
                                            @Override
                                            public void onSuccess(String content) {
                                                Log.i("xin", content);
                                                List<JSONObject> jsonObjects = JSON.parseArray(content, JSONObject.class);
                                                for (JSONObject i : jsonObjects) {

                                                    Log.i("xin", i.getString("lesson_name") + i.getString("week_start") + "  " + i.getString("week") + "   " + i.getString("day_start"));
                                                }
                                            }
                                        }
                                );
                                break;
                            }
                            case 404: {
                                //The password or username is not correct! do something !
                                Toast.makeText(getActivity(), getString(R.string.client_password_error), Toast.LENGTH_SHORT).show();
                                break;
                            }
                            case 201: {
                                //the account is not active !
                                Toast.makeText(getActivity(), getString(R.string.account_active), Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }
                    }
                });
            }
        }
        private boolean accessContent(){

            account = String.valueOf(accountEditText.getText());
            if(account.isEmpty()){
                Toast.makeText(getActivity(), getString(R.string.account_null), Toast.LENGTH_LONG).show();
                return false;
            }
            passCode = String.valueOf(passCodeEditText.getText());
            if(passCode.isEmpty()){
                Toast.makeText(getActivity(), getString(R.string.client_password_null), Toast.LENGTH_LONG).show();
                return false;
            }
            return true;
        }
    }
    private void initViewWidget(View rootView){

        button = (Button) rootView.findViewById(R.id.button_login);
        accountEditText = (EditText) rootView.findViewById(R.id.edit_text_login_account);
        passCodeEditText = (EditText) rootView.findViewById(R.id.edit_text_login_password);
        checkBox = (CheckBox) rootView.findViewById(R.id.checkbox_save_password);
    }

    //widget statement
    private Button button;
    private EditText accountEditText;
    private EditText passCodeEditText;
    private CheckBox checkBox;

}
