package com.example.ddnbinc.workforceplusplus.Fragments.Authentication.Login;

import android.app.Activity;
import android.app.FragmentManager;

import com.example.ddnbinc.workforceplusplus.DataBaseConnection.DataBaseConnectionPresenter;
import com.example.ddnbinc.workforceplusplus.Dialogs.Default.ProgressBarPresenter;
import com.example.ddnbinc.workforceplusplus.Fragments.Shift.ViewShift.ViewShift;
import com.example.ddnbinc.workforceplusplus.MainActivity;
import com.example.ddnbinc.workforceplusplus.R;
import com.example.ddnbinc.workforceplusplus.Users.Employee;
import com.example.ddnbinc.workforceplusplus.Users.Manager;
import com.example.ddnbinc.workforceplusplus.Users.TeamMember;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import com.google.firebase.database.ValueEventListener;


/**
 * Created by david on 2017-01-23.
 */

public class GetUserModel {
    private DataBaseConnectionPresenter dataBaseConnectionPresenter;
    private Activity mActivity;
    private String uId;
    private ProgressBarPresenter progressBarPresenter;
    private Employee employee;

    public GetUserModel(DataBaseConnectionPresenter dataBase, Activity activity, ProgressBarPresenter prog){
        dataBaseConnectionPresenter=dataBase;
        mActivity=activity;
        uId=dataBaseConnectionPresenter.getFirebaseUser().getUid().toString();
        progressBarPresenter=prog;
    }


    public Void getUser() {
        dataBaseConnectionPresenter.getDbReference().child("Users").child(uId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.hasChild("privilleges")) {
                        employee = dataSnapshot.getValue(Manager.class);
                    } else {
                        employee = dataSnapshot.getValue(TeamMember.class);
                    }
                    ((MainActivity)mActivity).setEmployee(employee);
                    progressBarPresenter.Hide();

                    FragmentManager fragmentManager = ((MainActivity)mActivity).getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_frame,new ViewShift()).commit();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return null;
    }


}
