package com.example.ddnbinc.workforceplusplus.Classes.Notifications;

import android.app.Activity;
import android.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ddnbinc.workforceplusplus.Adapters.NotificationRecycleAdapter;
import com.example.ddnbinc.workforceplusplus.ChatRoom.ChatRoom;
import com.example.ddnbinc.workforceplusplus.DataBaseConnection.DataBaseConnectionPresenter;
import com.example.ddnbinc.workforceplusplus.MainActivity;
import com.example.ddnbinc.workforceplusplus.Notifications.NotificationManager;
import com.example.ddnbinc.workforceplusplus.R;
import com.example.ddnbinc.workforceplusplus.Utilities.StringFormater;

/**
 * Created by davidhuang on 2017-03-27.
 */

public class MessageNotification implements Notification {
    private String Title;
    private String Message;
    private Long Timestamp;
    private String id;

    public MessageNotification(){

    }

    public MessageNotification(String Title, String Message, Long Timestamp){
        this.Title = Title;
        this.Message = Message;
        this.Timestamp = Timestamp;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
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
        view.setBackgroundResource(R.drawable.messagerec);
    }

    @Override
    public void setTitle(TextView title) {
        title.setText(this.Title);
    }

    @Override
    public void setDescription(TextView description) {
        description.setText(this.Message);
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


                FragmentManager fragmentManager = activity.getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, new ChatRoom()).commit();
            }
        });
    }


}
