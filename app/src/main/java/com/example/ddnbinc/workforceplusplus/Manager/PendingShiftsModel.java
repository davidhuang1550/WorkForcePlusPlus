package com.example.ddnbinc.workforceplusplus.Manager;

import android.app.Activity;
import android.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.example.ddnbinc.workforceplusplus.Adapters.PendingAdapter;
import com.example.ddnbinc.workforceplusplus.DataBaseConnection.DataBaseConnectionPresenter;
import com.example.ddnbinc.workforceplusplus.MainActivity;
import com.example.ddnbinc.workforceplusplus.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by davidhuang on 2017-02-19.
 */

public class PendingShiftsModel{
    private DataBaseConnectionPresenter dataBaseConnectionPresenter;
    private Activity mActivity;
    private ArrayList<String> Shifts;
    private ArrayList<String> ShiftsTaker;
    private ListView PendingShiftsView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private View mView;

    public PendingShiftsModel(Activity activity,ListView listView,SwipeRefreshLayout swipe,View view){
        mActivity=activity;
        PendingShiftsView=listView;
        swipeRefreshLayout=swipe;
        dataBaseConnectionPresenter= ((MainActivity)mActivity).getDataBaseConnectionPresenter();
        Shifts = new ArrayList<>();
        ShiftsTaker=new ArrayList<>();
        mView=view;
    }
    public void fetchShifts(){
        swipeRefreshLayout.setRefreshing(true);
        dataBaseConnectionPresenter.getDbReference().child("PendingConfirm").
                addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Shifts.add(data.getKey());
                    ShiftsTaker.add(data.getValue(String.class));
                }
                init();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void init(){
        PendingAdapter pendingAdapter = new PendingAdapter(mActivity,Shifts,ShiftsTaker,mView);
        PendingShiftsView.setAdapter(pendingAdapter);
        swipeRefreshLayout.setRefreshing(false);
    }


}
