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
                params.put("fcm_token", FirebaseInstanceId.getInstance().getToken());
                params.put("shift_id",givenUpShift.getShiftId());
                params.put("Taker",EmployeeId);

                return params;
            }
        };
        MySingleton.getmInstance(((MainActivity)mActivity)).addRequestQueue(stringRequest);




    }
}
