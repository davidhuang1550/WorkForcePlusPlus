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

public class PendingNotification implements Notification, Serializable {

    private Long Timestamp;
    private String Shift;
    private String Taker;
    private String id;

    public PendingNotification(){

    }

    public PendingNotification(Long timestamp, String Shift, String Taker){
        this.Timestamp = timestamp;
        this.Shift = Shift;
        this.Taker = Taker;
    }
    @Override
    public Long getTimestamp() {
        return Timestamp;
    }

    @Override
    public void setTimestamp(Long timestamp) {
        Timestamp = timestamp;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void setImageView(ImageView view) {
        view.setBackgroundResource(R.drawable.attention);
    }

    @Override
    public void setTitle(TextView title) {
        title.setText("Someone Wants to trade shifts");
    }

    @Override
    public void setDescription(TextView description) {
        description.setText("Shift Pending");
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
                NotificationManager.deleteNotification(id,(MainActivity) activity);

                ProgressBarPresenter progressBarPresenter = new ProgressBarPresenter(activity, "Loading");
                progressBarPresenter.Show();

                Bundle bundle = new Bundle();
                bundle.putString("Taker", Taker);
                bundle.putString("ShiftId", Shift);

                NotificationManager notificationManager = new NotificationManager(activity, bundle, progressBarPresenter);
                notificationManager.setView();
            }
        });
    }


    public String getShift() {
        return Shift;
    }

    public void setShift(String shift) {
        Shift = shift;
    }

    public String getTaker() {
        return Taker;
    }

    public void setTaker(String taker) {
        Taker = taker;
    }
}
