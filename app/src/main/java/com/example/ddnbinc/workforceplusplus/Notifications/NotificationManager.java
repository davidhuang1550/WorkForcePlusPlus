package com.example.ddnbinc.workforceplusplus.Notifications;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.os.Parcelable;

import com.example.ddnbinc.workforceplusplus.Classes.Shifts;
import com.example.ddnbinc.workforceplusplus.Classes.Users.Employee;
import com.example.ddnbinc.workforceplusplus.Classes.Users.Manager;
import com.example.ddnbinc.workforceplusplus.Classes.Users.TeamMember;
import com.example.ddnbinc.workforceplusplus.DataBaseConnection.DataBaseConnectionPresenter;
import com.example.ddnbinc.workforceplusplus.Dialogs.Default.ProgressBarPresenter;
import com.example.ddnbinc.workforceplusplus.Fragments.Shift.DecisionFragment;
import com.example.ddnbinc.workforceplusplus.Fragments.Shift.ResultFragment;
import com.example.ddnbinc.workforceplusplus.MainActivity;
import com.example.ddnbinc.workforceplusplus.R;
import com.example.ddnbinc.workforceplusplus.Utilities.StringFormater;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;

/**
 * Created by davidhuang on 2017-01-26.
 * manages the notifications
 */

public class NotificationManager extends StringFormater{

    private Activity mActivity;
    private Bundle bundle;
    private DataBaseConnectionPresenter dataBaseConnectionPresenter;
    private Employee[] employee;
    private Query query;
    private ProgressBarPresenter progressBarPresenter;
    private StringFormater stringFormater;
    private Shifts shifts;
    private String temp;

    public NotificationManager(Activity activity, Bundle b, ProgressBarPresenter prog){
        super();
        mActivity=activity;
        bundle=b;
        employee= new Employee[2];
        progressBarPresenter=prog;
        dataBaseConnectionPresenter=((MainActivity)mActivity).getDataBaseConnectionPresenter();
    }

/*
fetch all the nessasary data
 */
    public void setView(){
        temp = bundle.getString("Taker");
      if(temp!=null) {
          // manager accepting tab
          setReciever();
          setShift();
      }else{
          setShift();
          //normal team member
      }

    }
    public void setGiver(String id){
        query= dataBaseConnectionPresenter.getDbReference().child("Users").child(id);

        getusers(query,0);
    }
    public void setReciever(){
        query= dataBaseConnectionPresenter.getDbReference().child("Users").child(bundle.getString("Taker"));
        getusers(query,1);
    }
    public void getusers(Query query,final int u){
       query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChild("privilleges")){
                    employee[u] = dataSnapshot.getValue(Manager.class);
                }else{employee[u] = dataSnapshot.getValue(TeamMember.class);}
                progressBarPresenter.Hide();
                if(employee[0]!=null)LoadView();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public void setShift(){
        dataBaseConnectionPresenter.getDbReference().child("Shifts").child(bundle.getString("ShiftId")).
                addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                shifts = dataSnapshot.getValue(Shifts.class);
                shifts.setShiftId(dataSnapshot.getKey());
                if(temp!=null)setGiver(shifts.getEmployeeid());
                else{LoadView();}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    /*
    called once all info is gathered
     */
    public void LoadView(){
        try {
            Bundle new_bundle = new Bundle();

            stringFormater= new StringFormater();
            String time = stringFormater.Process(shifts.getStartTime(), shifts.getEndTime());

            bundle.putString("Time", time);
            FragmentManager fragmentManager = ((MainActivity)mActivity).getFragmentManager();
            if(temp!=null) {
                new_bundle.putSerializable("Shift", (Serializable) shifts);
                new_bundle.putParcelable("Giver", (Parcelable) employee[0]);
                new_bundle.putParcelable("Taker", (Parcelable) employee[1]);
                DecisionFragment decisionFragment = new DecisionFragment();
                decisionFragment.setArguments(new_bundle);
                fragmentManager.beginTransaction().add(R.id.content_frame,decisionFragment).commit();
            }else {
                new_bundle.putString("Response", bundle.getString("Response"));
                ResultFragment resultFragment = new ResultFragment();
                resultFragment.setArguments(new_bundle);
                fragmentManager.beginTransaction().add(R.id.content_frame,resultFragment).commit();
            }

        }catch (RuntimeException e){
            e.printStackTrace();
        }

    }





}
