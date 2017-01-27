package com.example.ddnbinc.workforceplusplus.Notifications;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.TextView;

import com.example.ddnbinc.workforceplusplus.Classes.Shifts;
import com.example.ddnbinc.workforceplusplus.Classes.Users.Employee;
import com.example.ddnbinc.workforceplusplus.Classes.Users.Manager;
import com.example.ddnbinc.workforceplusplus.Classes.Users.TeamMember;
import com.example.ddnbinc.workforceplusplus.DataBaseConnection.DataBaseConnectionPresenter;
import com.example.ddnbinc.workforceplusplus.Dialogs.Default.ProgressBarPresenter;
import com.example.ddnbinc.workforceplusplus.Fragments.Shift.DecisionFragment;
import com.example.ddnbinc.workforceplusplus.MainActivity;
import com.example.ddnbinc.workforceplusplus.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by davidhuang on 2017-01-26.
 * manages the notifications
 */

public class NotificationManager {

    private Activity mActivity;
    private Bundle bundle;
    private DataBaseConnectionPresenter dataBaseConnectionPresenter;
    private Employee[] employee;
    private Query query;
    private ProgressBarPresenter progressBarPresenter;
    private Shifts shifts;

    public NotificationManager(Activity activity, Bundle b, ProgressBarPresenter prog){
        mActivity=activity;
        bundle=b;
        employee= new Employee[2];
        progressBarPresenter=prog;
        dataBaseConnectionPresenter=((MainActivity)mActivity).getDataBaseConnectionPresenter();
    }


    public void setView(){
        String temp = bundle.getString("Taker");
      if(temp!=null) {
          // manager accepting tab
          setReciever();
          setShift();
      }else{
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
                if(u==0)LoadView();

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
                setGiver(shifts.getEmployeeid());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void LoadView(){
        Bundle bundle = new Bundle();
        bundle.putSerializable("Taker",(Serializable) employee[1]);
        bundle.putSerializable("Giver",(Serializable) employee[0]);
        bundle.putSerializable("Shift",(Serializable) shifts);
        String time = Process(shifts.getStartTime(),shifts.getEndTime());

        bundle.putString("Time",time);
        DecisionFragment decisionFragment = new DecisionFragment();
        decisionFragment.setArguments(bundle);

        FragmentManager fragmentManager = ((MainActivity)mActivity).getFragmentManager();
        fragmentManager.beginTransaction().add(R.id.content_frame,decisionFragment).commit();

    }
    public String Process(Long s,Long e){
        Long Start_time = (s % 86400);
        Long End_time = (e % 86400);

        String start = combine(Start_time / 3600,(Start_time % 3600) / 60,Start_time);
        String end = combine(End_time / 3600,(End_time % 3600) / 60,End_time);

        SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
        String dateString_begin = formatter.format(new Date(s * 1000L))+" "+start+"-"+end;
        return dateString_begin;
    }
    public String combine(Long hour, Long minutes, Long time){
        String AMPM;
        if (time >= 43200) {
            AMPM = " PM";
            if(time>=46800)hour-=12;
        } else AMPM = " AM";
        return  hour.toString()+":"+minutes.toString()+AMPM;
    }





}
