package com.example.ddnbinc.workforceplusplus.Notifications;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.ddnbinc.workforceplusplus.R;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by david on 2017-01-08.
 * gets invoked when the app is first installed and opened.
 * save the token in preferences and if someone logins then replace the new fcm token here
 */



public class NotificationServce extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        String recent_token = FirebaseInstanceId.getInstance().getToken();
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.FCM_PREF),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.FCM_TOKEN),recent_token);
        editor.commit();

    }
}
