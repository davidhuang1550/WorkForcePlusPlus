package com.example.ddnbinc.workforceplusplus.Notifications;

import android.app.Activity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.example.ddnbinc.workforceplusplus.Classes.GivenUpShift;
import com.example.ddnbinc.workforceplusplus.MainActivity;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by david on 2017-01-08.
 */

public class SendNotification {

    private Activity mActivity;
    private GivenUpShift givenUpShift;
    private String fcm="fgnZAtVfrgs:APA91bFurWhAxDEakW7UjtKL7Akp12m2hYDgCkEPcdiSweLPLu7AKobS7IxIxBVrusYMFK5rSrG6FqDVripH7Fwp7bL5S-Wy3i9KEaTqZpQBpsjUY1pShZRJVmKlbHzpnaCHwvaEuom9";
    /*
    url that contains php code that act as a meditator which uses CURL to communicate to the firebase server
     */
    private String app_server_url ="https://ddnbinc.000webhostapp.com/fcm_insert.php";
    private String EmployeeId;
    public SendNotification(Activity activity, GivenUpShift given,String id){
        mActivity=activity;
        givenUpShift=given;
        EmployeeId=id;


    }

    public void sendToken(){
        StringRequest stringRequest= new StringRequest(Request.Method.POST, app_server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                // replace the fcm_token get instance with a real fcm token
                /*
                set map will appear on the php server as a POST which they will then parse to send as title
                and body
                 */
                params.put("fcm_token", fcm);
                params.put("shift_id",givenUpShift.getShiftId());
                params.put("Taker",EmployeeId);

                return params;
            }
        };
        MySingleton.getmInstance(((MainActivity)mActivity)).addRequestQueue(stringRequest);




    }
}
