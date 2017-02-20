package com.example.ddnbinc.workforceplusplus.Fragments.Email;

import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import com.example.ddnbinc.workforceplusplus.Adapters.EmailAdapter;
import com.example.ddnbinc.workforceplusplus.DataBaseConnection.DataBaseConnectionPresenter;
import com.example.ddnbinc.workforceplusplus.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davidhuang on 2017-02-18.
 */

public class EmailModel {
    private DataBaseConnectionPresenter dataBaseConnectionPresenter;
    private Activity mActivity;
    private ArrayList<String> Emails;
    private ListView EmailView;
    private SwipeRefreshLayout swipeRefreshLayout;

    public EmailModel(Activity activity, ListView listView, SwipeRefreshLayout swipe){
        mActivity=activity;
        dataBaseConnectionPresenter= ((MainActivity)mActivity).getDataBaseConnectionPresenter();
        Emails=new ArrayList<>();
        swipeRefreshLayout=swipe;
        EmailView=listView;
    }
    public void getEmailList(){
        dataBaseConnectionPresenter.getDbReference().child("Emails").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Emails = (ArrayList<String>) dataSnapshot.getValue();
                setView();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public void setView(){
        EmailAdapter emailAdapter = new EmailAdapter(Emails,mActivity);
        EmailView.setAdapter(emailAdapter);
        swipeRefreshLayout.setRefreshing(false);

    }

}
