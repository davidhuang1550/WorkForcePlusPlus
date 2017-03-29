package com.example.ddnbinc.workforceplusplus.Classes.Notifications;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ddnbinc.workforceplusplus.Adapters.NotificationRecycleAdapter;
import com.example.ddnbinc.workforceplusplus.DataBaseConnection.DataBaseConnectionPresenter;
import com.example.ddnbinc.workforceplusplus.Dialogs.Default.ProgressBarPresenter;
import com.example.ddnbinc.workforceplusplus.MainActivity;
import com.example.ddnbinc.workforceplusplus.Notifications.NotificationManager;
import com.example.ddnbinc.workforceplusplus.R;
import com.example.ddnbinc.workforceplusplus.Utilities.StringFormater;

import java.io.Serializable;

/**
 * Created by davidhuang on 2017-03-27.
 */

public class ResponseNotification implements  Notification, Serializable {
    private String Response;
    private Long Timestamp;
    private String id;

    public ResponseNotification(){

    }

    public ResponseNotification(String Response, Long Timestamp){
        this.Response = Response;
        this.Timestamp = Timestamp;
    }

    @Override
    public Long getTimestamp() {
        return Timestamp;
    }

    @Override
    public void setTimestamp(Long timestamp) {
        this.Timestamp = timestamp;
    }

    @Override
    public void setImageView(ImageView view) {
        view.setBackgroundResource(R.drawable.attention);
    }

    @Override
    public void setTitle(TextView title) {
        title.setText("Response");
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @Override
    public void setDescription(TextView description) {
            description.setText("Shift trading response");
    }

    @Override
    public void setTimestampView(TextView timestamp) {
        timestamp.setText(StringFormater.getmInstance().time(this.Timestamp));
    }

    @Override
    public void setListener(NotificationRecycleAdapter.ViewHolder viewHolder, final Activity activity) {
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //delete the notifications
                NotificationManager.deleteNotification(id,(MainActivity) activity);

                ProgressBarPresenter progressBarPresenter = new ProgressBarPresenter(activity, "Loading");
                progressBarPresenter.Show();

                Bundle bundle = new Bundle();
                bundle.putString("Response",Response);
                NotificationManager notificationManager = new NotificationManager(activity, bundle, progressBarPresenter);
                notificationManager.setView();
            }
        });
    }

}
