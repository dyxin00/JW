package com.qdu.jw.app.linkServer;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;

/**
 * Created by xin on 5/15/14.
 */
public class CreateAsyncHttpClient {

    private volatile static AsyncHttpClient client = null;
    private CreateAsyncHttpClient(){

    }

    public static AsyncHttpClient createClient(android.content.Context context){

        if(client == null){
            synchronized (CreateAsyncHttpClient.class){
                if(client == null){
                    client = new AsyncHttpClient();
                    client.setCookieStore(new PersistentCookieStore(context));
                }
            }
        }
        return client;
    }
    public static AsyncHttpClient getClient(){

        return client;
    }
}
