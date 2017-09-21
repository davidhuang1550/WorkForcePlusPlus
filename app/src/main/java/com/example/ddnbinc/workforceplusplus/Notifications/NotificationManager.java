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
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;

/**
 * Created by davidhuang on 2017-01-26.
 * manages the notifications
 */

public class NotificationManager{

    private Activity mActivity;
    private Bundle bundle;
    private DataBaseConnectionPresenter dataBaseConnectionPresenter;
    private Employee[] employee;
    private Query query;
    private ProgressBarPresenter progressBarPresenter;
    private Shifts shifts;
    private String temp;


    /**
     * CONSTRUCTOR
     * @param activity  = MainActivity
     * @param b = Bundle contains the reason for the notifications ie ( Response or Pending shift etc)
     * @param prog = presenter for the progress bar because we started the loading in the mainactivity
     */
    public NotificationManager(Activity activity, Bundle b, ProgressBarPresenter prog){
        super();
        mActivity=activity;
        bundle=b;
        employee= new Employee[2];
        progressBarPresenter=prog;
        dataBaseConnectionPresenter=((MainActivity)mActivity).getDataBaseConnectionPresenter();
    }

    /*
     * fetch all the nessasary data
     * TODO clean this up
     */
    public void setView(){
        temp = bundle.getString("Taker");

        // delete the notification
        deleteNotification((String)bundle.get("Notification_ID"),(MainActivity) mActivity);

        if(dataBaseConnectionPresenter!=null) {
            if (temp != null) {
                // manager accepting tab
                setReciever();
                setShift();
            } else {
                LoadView();
                //normal team member
            }
        }

    }

    /*
     * @param notification_id = notification id that is used to delete the spcific notification
     * @param mActivity = since this is a static function we have to pass in the activity to fetch the Database
     * connection object. Consider making DatabaseConnection a singleton so we dont need to keep doing this.
     */
    public  static void deleteNotification(String notification_id, MainActivity mActivity){
        DataBaseConnectionPresenter dataBaseConnectionPresenter =((MainActivity)mActivity).getDataBaseConnectionPresenter();
          try {
              dataBaseConnectionPresenter.getDbReference().child("Users")
                      .child(((MainActivity) mActivity)
                      .getEmployee().getEmployeeId())
                      .child("Notifications").child(notification_id).setValue(null);
            }catch (DatabaseException e){
                e.printStackTrace();
            }
    }

    /*
     * @param id = the person giving up the shift id
     * fetch the user since we are only given the id of the user
     */
    public void setGiver(String id){
        query= dataBaseConnectionPresenter.getDbReference().child("Users").child(id);

        getusers(query,0);
    }
    /*
     * temp is declared in setView we only call this if the notification is for
     * pending shifts
     */
    public void setReciever(){
        query= dataBaseConnectionPresenter.getDbReference().child("Users").child(temp);
        getusers(query,1);
    }

    /*
     * @param Query = specifies the query needed to fetch the abstract information
     * @param u = is the users we are fetching, since we are fetching 2 users we want to call
     * set view once we fetch both users.
     */
    private void getusers(Query query,final int u){
       query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    if (dataSnapshot.hasChild("privilleges")) {
                        employee[u] = dataSnapshot.getValue(Manager.class);
                    } else {
                        employee[u] = dataSnapshot.getValue(TeamMember.class);
                    }
                    progressBarPresenter.Hide();
                    if (employee[0] != null) LoadView();
                }catch (DatabaseException e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    /*
     * fetch this shift that is pending.
     */
    public void setShift(){
        dataBaseConnectionPresenter.getDbReference().child("Shifts").child(bundle.getString("ShiftId")).
                addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    shifts = dataSnapshot.getValue(Shifts.class);
                    shifts.setShiftId(dataSnapshot.getKey());
                    setGiver(shifts.getEmployeeid());
                }catch (DatabaseException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    /*
    called once all info is gathered
     */
    private void LoadView(){
        try {
            Bundle new_bundle = new Bundle();


            String time = StringFormater.getmInstance().Process(shifts.getStartTime(), shifts.getEndTime());

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
