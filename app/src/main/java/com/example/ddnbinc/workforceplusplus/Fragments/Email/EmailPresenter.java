package com.example.ddnbinc.workforceplusplus.Fragments.Email;

import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

/**
 * Created by davidhuang on 2017-02-18.
 */

public class EmailPresenter {
    private EmailModel emailModel;

    public EmailPresenter(Activity activity, ListView listView, SwipeRefreshLayout swipeRefreshLayout){
        emailModel = new EmailModel(activity,listView,swipeRefreshLayout);
    }
    public void GetEmail(){
        emailModel.getEmailList();
    }

}
