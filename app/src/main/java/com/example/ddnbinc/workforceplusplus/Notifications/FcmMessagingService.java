package com.example.ddnbinc.workforceplusplus.Notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.example.ddnbinc.workforceplusplus.MainActivity;
import com.example.ddnbinc.workforceplusplus.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by david on 2017-01-08.
 * create notifications
 */

public class FcmMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String shift = remoteMessage.getNotification().getTitle();
        String users = remoteMessage.getNotification().getBody();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(users);


        // put bundle into intent then open it in main activity to open. fragment post


        /*
        once the notification is clicked on the intent will be fired and invoke a method on the main activity container
        that will directly try to fetch the current logged in user then display the pending shift
         */

            // TODO maybe try to pass an object instead of strings
            Intent intent = new Intent(this,MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("ShiftId",shift);
            String title;

            String Response = jsonObject.getString("Taker");
            String Notification = jsonObject.getString("notification");

            bundle.putString("Notification_ID",Notification);
            if(Response.equals("Denied")||Response.equals("Approved")){

                //TODO pass object instead
                bundle.putString("Response",Response);
                title = "Swap Shift Response";
            }
            else {
                bundle.putString("Taker", Response);
                title="Swap Shift Pending";
            }

             intent.putExtra("Info",bundle);

            PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
            notificationBuilder.setContentTitle(title);
            notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
            notificationBuilder.setAutoCancel(true);
            notificationBuilder.setOnlyAlertOnce(true);
            notificationBuilder.setContentIntent(pendingIntent);
            NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0,notificationBuilder.build());

        } catch (JSONException e) {
            e.printStackTrace();
        }





    }
}
