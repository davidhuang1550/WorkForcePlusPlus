package com.example.ddnbinc.workforceplusplus.Classes.Notifications;

import android.app.Activity;
import android.app.FragmentManager;
import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ddnbinc.workforceplusplus.Adapters.NotificationRecycleAdapter;
import com.example.ddnbinc.workforceplusplus.R;

import java.io.Serializable;
import java.security.Timestamp;

/**
 * Created by davidhuang on 2017-03-05.
 */

public interface Notification {
    public Long getTimestamp();

    public void setTimestamp(Long timestamp);

    public void setImageView(ImageView view);

    public void setTitle(TextView title);

    public void setDescription(TextView description);

    public void setTimestampView(TextView timestamp);

    public void setListener(NotificationRecycleAdapter.ViewHolder viewHolder, final Activity activity);
}
