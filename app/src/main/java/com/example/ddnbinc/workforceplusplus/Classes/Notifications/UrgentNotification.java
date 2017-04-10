package com.example.ddnbinc.workforceplusplus.Classes.Notifications;

import android.app.Activity;
import android.app.FragmentManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ddnbinc.workforceplusplus.Adapters.NotificationRecycleAdapter;
import com.example.ddnbinc.workforceplusplus.R;

import java.io.Serializable;

/**
 * Created by davidhuang on 2017-03-27.
 */

public class UrgentNotification implements  Notification , Serializable {
    @Override
    public Long getTimestamp() {
        return null;
    }

    @Override
    public void setTimestamp(Long timestamp) {

    }

    @Override
    public void setImageView(ImageView view) {
        view.setBackgroundResource(R.drawable.urgent);

    }

    @Override
    public void setTitle(TextView title) {

    }

    @Override
    public void setDescription(TextView description) {

    }

    @Override
    public void setTimestampView(TextView timestamp) {

    }

    @Override
    public void setListener(NotificationRecycleAdapter.ViewHolder viewHolder, Activity activity) {

    }

    @Override
    public void setId(String id) {

    }

}
