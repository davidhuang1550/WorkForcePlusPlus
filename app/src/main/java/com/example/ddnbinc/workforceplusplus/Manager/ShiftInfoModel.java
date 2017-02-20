package com.example.ddnbinc.workforceplusplus.Manager;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.ddnbinc.workforceplusplus.Classes.Shifts;
import com.example.ddnbinc.workforceplusplus.Classes.Users.Employee;
import com.example.ddnbinc.workforceplusplus.Classes.Users.Manager;
import com.example.ddnbinc.workforceplusplus.Classes.Users.TeamMember;
import com.example.ddnbinc.workforceplusplus.DataBaseConnection.DataBaseConnectionPresenter;
import com.example.ddnbinc.workforceplusplus.Fragments.Shift.DecisionFragment;
import com.example.ddnbinc.workforceplusplus.MainActivity;
import com.example.ddnbinc.workforceplusplus.R;
import com.example.ddnbinc.workforceplusplus.Utilities.StringFormater;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by davidhuang on 2017-02-19.
 */

public class ShiftInfoModel extends StringFormater{

    private Activity mActivity;
    private String ShiftInfo;
    private String Taker;
    private DataBaseConnectionPresenter dataBaseConnectionPresenter;
    private Shifts shifts;
    private Employee[] employee;
    private Bundle bundle;



    public ShiftInfoModel(Activity activity, String shift, String Take){
        mActivity=activity;
        ShiftInfo=shift;
        Taker=Take;
        dataBaseConnectionPresenter= ((MainActivity)mActivity).getDataBaseConnectionPresenter();
        employee = new Employee[2];
        bundle= new Bundle();
    }

    public void getShiftInfo(){
        dataBaseConnectionPresenter.getDbReference().child("Shifts").child(ShiftInfo)
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                shifts = dataSnapshot.getValue(Shifts.class);
                getusers();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getusers(){

        getGetterInfo(dataBaseConnectionPresenter.getDbReference().child("Users").child(shifts.getEmployeeid()),0);

    }



    public  void getGetterInfo(Query query, final int i){
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild("privilleges")) {
                        employee[i] = dataSnapshot.getValue(Manager.class);
                        //  ((MainActivity)mActivity).showManagerView();
                    } else {
                        employee[i] = dataSnapshot.getValue(TeamMember.class);
                        //    ((MainActivity)mActivity).hideManagerView();
                    }
                    employee[i].setEmployeeId(dataSnapshot.getKey());


                getTakerInfo(dataBaseConnectionPresenter.getDbReference().child("Users").orderByChild("email").equalTo(Taker),1);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public  void getTakerInfo(Query query, final int i){
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    if (dataSnapshot.hasChild("privilleges")) {
                        employee[i] = snapshot.getValue(Manager.class);
                        //  ((MainActivity)mActivity).showManagerView();
                    } else {
                        employee[i] = snapshot.getValue(TeamMember.class);
                        //    ((MainActivity)mActivity).hideManagerView();
                    }
                    employee[i].setEmployeeId(dataSnapshot.getKey());
                    break;
                }
                View();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void setView(View view){
        getShiftInfo();
        //Bundle bundle =new Bundle();



       // return bundle;
    }

    public void View(){
        bundle.putParcelable("Taker",employee[1]);
        bundle.putParcelable("Giver",employee[0]);
        bundle.putSerializable("Shift",shifts);

        String temp = Process(shifts.getStartTime(),shifts.getEndTime());
        bundle.putString("Time",temp);


        DecisionFragment decisionFragment = new DecisionFragment();
        decisionFragment.setArguments(bundle);


        FragmentTransaction transaction = ((MainActivity) mActivity).getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.animator.enter_anim, R.animator.exit_anim,
                R.animator.enter_anim_back, R.animator.exit_anime_back);
        transaction.add(R.id.content_frame, decisionFragment).addToBackStack("adapter").commit();
    }


}
