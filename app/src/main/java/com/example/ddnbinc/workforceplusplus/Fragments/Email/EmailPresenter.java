package com.example.ddnbinc.workforceplusplus.Fragments.Email;

import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

/**
 * Created by davidhuang on 2017-02-18.
 */

public class EmailPresenter {
    private EmailModel emailModel;
    private SwipeRefreshLayout swipeRefreshLayout;

    public EmailPresenter(Activity activity, ListView listView, SwipeRefreshLayout swipe){
        swipeRefreshLayout = swipe;
        emailModel = new EmailModel(activity,listView,swipe);
    }
    public void GetEmail(){
        try {
            emailModel.getEmailList();
        }catch (NullPointerException e){
            swipeRefreshLayout.setRefreshing(false);
            e.printStackTrace();
        }
    }

}
