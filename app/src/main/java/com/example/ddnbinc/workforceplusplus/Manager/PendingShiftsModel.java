package com.example.ddnbinc.workforceplusplus.Manager;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ddnbinc.workforceplusplus.Adapters.PendingAdapter;
import com.example.ddnbinc.workforceplusplus.DataBaseConnection.DataBaseConnectionPresenter;
import com.example.ddnbinc.workforceplusplus.Error.ErrorView;
import com.example.ddnbinc.workforceplusplus.MainActivity;
import com.example.ddnbinc.workforceplusplus.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
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

    /**
     *
     * @param activity most likely to be mainactivity
     * @param listView listview that we must populate
     * @param swipe the refresh layout that we must stop refreshing once we finish loading.
     */
    public PendingShiftsModel(Activity activity,ListView listView,SwipeRefreshLayout swipe){
        mActivity=activity;
        PendingShiftsView=listView;
        swipeRefreshLayout=swipe;
        dataBaseConnectionPresenter= ((MainActivity)mActivity).getDataBaseConnectionPresenter();
        Shifts = new ArrayList<>();
        ShiftsTaker=new ArrayList<>();
    }
    public void fetchShifts(){
        swipeRefreshLayout.setRefreshing(true);
        try {
            dataBaseConnectionPresenter.getDbReference().child("PendingConfirm").
                    addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            try {
                                for (DataSnapshot data : dataSnapshot.getChildren()) {
                                    Shifts.add(data.getKey());
                                    ShiftsTaker.add(data.getValue(String.class));
                                }
                                init();
                            }catch (DatabaseException e){
                                e.printStackTrace();
                                swipeRefreshLayout.setRefreshing(false);
                                Toast.makeText(mActivity,"An error has occured", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        }catch (NullPointerException e){
            swipeRefreshLayout.setRefreshing(false);
            e.printStackTrace();
        }
    }
    public void init(){
        if(Shifts.size() ==0){
            FragmentManager fragmentManager = mActivity.getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame,new ErrorView()).commit();
            // leave.

        } else {
            PendingAdapter pendingAdapter = new PendingAdapter(mActivity,Shifts,ShiftsTaker);
            PendingShiftsView.setAdapter(pendingAdapter);
        }
        swipeRefreshLayout.setRefreshing(false);
    }


}
