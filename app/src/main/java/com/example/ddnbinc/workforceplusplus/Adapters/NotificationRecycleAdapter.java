package com.example.ddnbinc.workforceplusplus.Adapters;

import android.app.Activity;
import android.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ddnbinc.workforceplusplus.Classes.Notifications.MessageNotification;
import com.example.ddnbinc.workforceplusplus.Classes.Notifications.Notification;
import com.example.ddnbinc.workforceplusplus.Classes.Notifications.PendingNotification;
import com.example.ddnbinc.workforceplusplus.Classes.Notifications.ResponseNotification;
import com.example.ddnbinc.workforceplusplus.Classes.Notifications.UrgentNotification;
import com.example.ddnbinc.workforceplusplus.R;

import java.util.ArrayList;

/**
 * Created by davidhuang on 2017-03-10.
 */

public class NotificationRecycleAdapter extends RecyclerView.Adapter<NotificationRecycleAdapter.ViewHolder> {
    private ArrayList<Notification> notifications;
    private Activity mActivity;

    public NotificationRecycleAdapter(ArrayList<Notification> noti, Activity activity){
        notifications=noti;
        mActivity=activity;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_tab, parent, false);

        ViewHolder holder = new ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Notification notification = notifications.get(position);

        notification.setImageView(holder.ImageType);
        notification.setDescription(holder.Description);
        notification.setTimestampView(holder.Timestamp);
        notification.setTitle(holder.Title);

        ((ResponseNotification) notification).setListener(holder,mActivity);

    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        ImageView ImageType;
        TextView Title;
        TextView Description;
        TextView Timestamp;
        public ViewHolder(View itemView) {
            super(itemView);
            ImageType = (ImageView)itemView.findViewById(R.id.notification_image);
            Title = (TextView)itemView.findViewById(R.id.notification_title);
            Description = (TextView)itemView.findViewById(R.id.notification_description);
            Timestamp = (TextView)itemView.findViewById(R.id.notification_timestamp);
        }
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

    }
}
