package com.example.ddnbinc.workforceplusplus.Adapters;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ddnbinc.workforceplusplus.Classes.Notification;
import com.example.ddnbinc.workforceplusplus.R;
import com.example.ddnbinc.workforceplusplus.Utilities.StringFormater;

import java.util.ArrayList;

/**
 * Created by davidhuang on 2017-03-10.
 */

public class NotificationRecycleAdapter extends RecyclerView.Adapter<NotificationRecycleAdapter.ViewHolder> {
    private ArrayList<Notification> notifications;

    public NotificationRecycleAdapter(ArrayList<Notification> noti){
        notifications=noti;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_tab, parent, false);

        ViewHolder holder = new ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.Description.setText(notifications.get(position).getMessage());
        notifications.get(position).setView(holder.ImageType);
        holder.Timestamp.setText(StringFormater.getmInstance().NotificationTime(notifications.get(position).getTimestamp()));
        holder.Title.setText(notifications.get(position).getTitle());
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
