package com.example.ddnbinc.workforceplusplus.Notifications;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.ddnbinc.workforceplusplus.MainActivity;
import com.example.ddnbinc.workforceplusplus.R;


/**
 * Created by davidhuang on 2017-03-05.
 * once a notification is sent here add it also to pending notifications and if a notification is clicked
 * then click onto it would remove it from the notification tab
 **/

public class NotificationTab extends Fragment {
    private View mView;
    private Activity mActivity;
    private NotificationTabPresenter notificationTabPresenter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity= getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.notification_list,container,false);
        ((MainActivity)mActivity).setTitle("Notifications");

        RecyclerView recylerview = (RecyclerView) mView.findViewById(R.id.recylerview);
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)mView.findViewById(R.id.swiperefresh);
        notificationTabPresenter = new NotificationTabPresenter(mActivity,recylerview,swipeRefreshLayout);
        recylerview.setHasFixedSize(true);
        swipeRefreshLayout.setEnabled(false);

        LinearLayoutManager llm = new LinearLayoutManager(mActivity);
        recylerview.setLayoutManager(llm);
        notificationTabPresenter.SetView();

        return mView;
    }

    public void init(){

    }
}
