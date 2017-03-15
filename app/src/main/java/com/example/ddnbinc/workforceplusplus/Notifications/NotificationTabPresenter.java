package com.example.ddnbinc.workforceplusplus.Notifications;

import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import com.example.ddnbinc.workforceplusplus.MainActivity;

/**
 * Created by davidhuang on 2017-03-05.
 */

public class NotificationTabPresenter {
    private NotificationTabManager notificationManager;

    public NotificationTabPresenter(Activity activity, RecyclerView recyclerView, SwipeRefreshLayout swipeRefreshLayout){
        notificationManager = new NotificationTabManager(activity,recyclerView,swipeRefreshLayout);
    }
    public void SetView(){
        notificationManager.setNotifications();
    }
}
