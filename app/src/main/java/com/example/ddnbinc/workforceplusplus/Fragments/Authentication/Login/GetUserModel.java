package com.example.ddnbinc.workforceplusplus.Fragments.Authentication.Login;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.ddnbinc.workforceplusplus.DataBaseConnection.DataBaseConnectionPresenter;
import com.example.ddnbinc.workforceplusplus.Dialogs.Default.ProgressBarPresenter;
import com.example.ddnbinc.workforceplusplus.Fragments.Shift.ViewShift.ViewShift;
import com.example.ddnbinc.workforceplusplus.MainActivity;
import com.example.ddnbinc.workforceplusplus.Notifications.NotificationManager;
import com.example.ddnbinc.workforceplusplus.R;
import com.example.ddnbinc.workforceplusplus.Classes.Users.Employee;
import com.example.ddnbinc.workforceplusplus.Classes.Users.Manager;
import com.example.ddnbinc.workforceplusplus.Classes.Users.TeamMember;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;


/**
 * Created by david on 2017-01-23.
 */

public class GetUserModel {
    private DataBaseConnectionPresenter dataBaseConnectionPresenter;
    private Activity mActivity;
    private String uId;
    private ProgressBarPresenter progressBarPresenter;
    private Employee employee;
    private boolean flag;
    private NotificationManager notificationManager;

    public GetUserModel(DataBaseConnectionPresenter dataBase, Activity activity, ProgressBarPresenter prog){
        dataBaseConnectionPresenter=dataBase;
        mActivity=activity;
        progressBarPresenter=prog;
        notificationManager=null;
    }

    public void setFlag(NotificationManager not){
        notificationManager=not;
    }

    public Void getUser() {
        if(dataBaseConnectionPresenter.getFirebaseUser()!=null) {
            uId = dataBaseConnectionPresenter.getFirebaseUser().getUid().toString();
            dataBaseConnectionPresenter.getDbReference().child("Users").child(uId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.hasChild("privilleges")) {
                        employee = dataSnapshot.getValue(Manager.class);
                    } else {
                        employee = dataSnapshot.getValue(TeamMember.class);
                    }
                    employee.setEmployeeId(dataSnapshot.getKey());

                    ((MainActivity)mActivity).setEmployee(employee);
                    ((MainActivity)mActivity).setUserFcmToken();
                    progressBarPresenter.Hide();
                    if(notificationManager==null) {
                        redirect_ViewShift();
                    }else{
                        notificationManager.setView();
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }else{redirect_login();}
        return null;
    }
    public void redirect_login(){
        FragmentManager fragmentManager = ((MainActivity)mActivity).getFragmentManager();
        fragmentManager.beginTransaction().add(R.id.content_frame,new Login()).commit();
    }
    public void redirect_ViewShift(){
        FragmentManager fragmentManager = ((MainActivity)mActivity).getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame,new ViewShift()).commit();
    }


}
