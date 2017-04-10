package com.example.ddnbinc.workforceplusplus.Notifications;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by david on 2017-01-08.
 */

public class RequestSingletonQueue {
    private static RequestSingletonQueue mInstance;
    private Context context;
    private RequestQueue requestQueue;

    private RequestSingletonQueue(Context con){
        context=con;
        requestQueue=getRequestQueue();
    }

    private RequestQueue getRequestQueue(){
        if(requestQueue==null){
            requestQueue= Volley.newRequestQueue(context.getApplicationContext());
        }

        return requestQueue;
    }
    public static synchronized RequestSingletonQueue getmInstance(Context con){
        if(mInstance==null){
            mInstance= new RequestSingletonQueue(con);
        }
        return mInstance;
    }
    public<T> void addRequestQueue(Request<T> request){
        getRequestQueue().add(request);
    }
}
