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
    /**
     *
     * @return
     */
    public Long getTimestamp();

    /**
     *
     * @param timestamp
     */
    public void setTimestamp(Long timestamp);

    /**
     *
     * @param view
     */
    public void setImageView(ImageView view);

    /**
     *
     * @param title
     */
    public void setTitle(TextView title);

    /**
     *
     * @param description
     */
    public void setDescription(TextView description);

    /**
     *
     * @param timestamp
     */
    public void setTimestampView(TextView timestamp);

    /**
     *
     * @param viewHolder contains the whole view in which we must create a listener for dynamically
     * @param activity
     */
    public void setListener(NotificationRecycleAdapter.ViewHolder viewHolder, final Activity activity);

    /**
     *
     * @param id
     */
    public void setId(String id);
}
