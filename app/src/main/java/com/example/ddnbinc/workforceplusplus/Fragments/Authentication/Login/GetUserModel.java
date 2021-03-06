package com.example.ddnbinc.workforceplusplus.Fragments.Authentication.Login;

import android.app.Activity;
import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ddnbinc.workforceplusplus.DataBaseConnection.DataBaseConnectionPresenter;
import com.example.ddnbinc.workforceplusplus.Dialogs.Default.ProgressBarPresenter;
import com.example.ddnbinc.workforceplusplus.Fragments.Shift.Shift;
import com.example.ddnbinc.workforceplusplus.MainActivity;
import com.example.ddnbinc.workforceplusplus.Notifications.NotificationManager;
import com.example.ddnbinc.workforceplusplus.R;
import com.example.ddnbinc.workforceplusplus.Classes.Users.Employee;
import com.example.ddnbinc.workforceplusplus.Classes.Users.Manager;
import com.example.ddnbinc.workforceplusplus.Classes.Users.TeamMember;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


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
        /*
        this extra prevention is used specifically for the container acitivty

         */
        if(dataBaseConnectionPresenter.getFirebaseUser()!=null) {
            uId = dataBaseConnectionPresenter.getFirebaseUser().getUid().toString();
            dataBaseConnectionPresenter.getDbReference().child("Users").child(uId)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.hasChild("privileges")) {
                        employee = dataSnapshot.getValue(Manager.class);
                        ((MainActivity)mActivity).showManagerView();
                    } else {
                        employee = dataSnapshot.getValue(TeamMember.class);
                        ((MainActivity)mActivity).hideManagerView();
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
                    progressBarPresenter.Hide();
                    Toast.makeText(mActivity,"Please Check your internet Connetion", Toast.LENGTH_LONG).show();
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
        Bundle bundle = new Bundle();
        bundle.putString("name", "Your Shifts");
        bundle.putBoolean("type",false);
        Shift shift = new Shift();
        shift.setArguments(bundle);
        ((MainActivity)mActivity).showStatusBar();
        FragmentManager fragmentManager = ((MainActivity)mActivity).getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, shift).commit();
    }


}
