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
    private String givenUpShift;

    private String fcm;
    private String Response;

    /*

    url that contains php code that act as a meditator which uses CURL to communicate to the firebase server

     */

    private String app_server_url ="https://ddnbinc.000webhostapp.com/fcm_insert.php";
    private String EmployeeId; // this can be employee id or a response
    public SendNotification(Activity activity, String shift,String id,String fm){
        mActivity=activity;
        givenUpShift=shift;
        EmployeeId=id;
        fcm=fm;
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
                params.put("shift_id",givenUpShift);
                params.put("Taker",EmployeeId);

                return params;
            }
        };
        MySingleton.getmInstance(((MainActivity)mActivity)).addRequestQueue(stringRequest);




    }
}
