package com.example.ddnbinc.workforceplusplus.Fragments.Shift;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ddnbinc.workforceplusplus.Classes.GivenUpShift;
import com.example.ddnbinc.workforceplusplus.Classes.Notifications.PendingNotification;
import com.example.ddnbinc.workforceplusplus.Classes.Shifts;
import com.example.ddnbinc.workforceplusplus.Classes.Users.Employee;
import com.example.ddnbinc.workforceplusplus.Classes.Users.Manager;
import com.example.ddnbinc.workforceplusplus.DataBaseConnection.DataBaseConnectionPresenter;
import com.example.ddnbinc.workforceplusplus.Dialog.ConfirmationDialog;
import com.example.ddnbinc.workforceplusplus.MainActivity;
import com.example.ddnbinc.workforceplusplus.Notifications.SendNotification;
import com.example.ddnbinc.workforceplusplus.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by davidhuang on 2017-01-25.
 */

public class Confirm_GivenUpShift extends Fragment implements  View.OnClickListener{
    private View myView;
    private GivenUpShift givenUpShift;
    private TextView time;
    private Activity mActivity;
    private DataBaseConnectionPresenter dataBaseConnectionPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity= getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.swap_shift_confirm,container,false);
        dataBaseConnectionPresenter = ((MainActivity)mActivity).getDataBaseConnectionPresenter();


        ((MainActivity)mActivity).hideFab();
        Bundle bundle = getArguments();

        time = (TextView)myView.findViewById(R.id.time);

        if(bundle!=null){
            givenUpShift= (GivenUpShift) bundle.getSerializable("GivenUpShift");
            time.setText(bundle.getString("Times"));

        }
        Button yes = (Button)myView.findViewById(R.id.yes);
        Button no = (Button)myView.findViewById(R.id.no);


        yes.setOnClickListener(this);
        no.setOnClickListener(this);



        return myView;
    }

    public void checkConflictShifts(){
        String employeeId = ((MainActivity)mActivity).getEmployee().getEmployeeId();
        dataBaseConnectionPresenter.getDbReference().child("Users").child(employeeId).child("Shifts")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        boolean okay= true;
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            Shifts shift = snapshot.getValue(Shifts.class);
                            if((shift.getStartTime() < givenUpShift.getStartTime() &&
                                    shift.getEndTime() > givenUpShift.getStartTime())
                                    || shift.getStartTime()> givenUpShift.getStartTime() &&
                                    shift.getEndTime()<givenUpShift.getEndTime()){
                                okay = false;
                                break;
                            }

                        }
                        if(okay) {
                            sendNotification();
                            ((MainActivity) mActivity).onBackPressed();
                        }else{
                            ConfirmationDialog confirmationDialog = new ConfirmationDialog();
                            Bundle bundle = new Bundle();
                            bundle.putString("Title","Request Failed");
                            bundle.putString("Message","This shift conflicts with your other shifts");
                            confirmationDialog.setArguments(bundle);
                            confirmationDialog.show(((MainActivity) mActivity).getFragmentManager(), "Alert Dialog Fragment");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.yes:
                checkConflictShifts();

                break;
            case R.id.no:
                ((MainActivity) mActivity).onBackPressed();
                break;

        }

    }
    public void sendNotification(){
        try {
            dataBaseConnectionPresenter.getDbReference().child("Users").child("SY6qNTB8EsbNoJ4qjQwX8goWmHE3")
                    .child("fcmToken").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                        String fcm = dataSnapshot.getValue(String.class),
                        GivenUpShift = givenUpShift.getShiftId(),
                        EmployeeId =((MainActivity) mActivity).getEmployee().getEmployeeId();


                        // store the notification inside the users notification tab
                        PendingNotification pendingNotification = new PendingNotification(System.currentTimeMillis()/1000,
                                GivenUpShift,EmployeeId);

                        // TODO make it so that we can send these notifications to all managers.
                        DatabaseReference reference =  dataBaseConnectionPresenter.getDbReference().child("Users").child("SY6qNTB8EsbNoJ4qjQwX8goWmHE3")
                                .child("Notifications").push();

                        try {
                            reference.setValue(pendingNotification);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        SendNotification sendNotification = new SendNotification(mActivity, GivenUpShift, EmployeeId, fcm,reference.getKey());

                        MoveToPending();
                        sendNotification.sendToken();


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            ConfirmationDialog confirmationDialog = new ConfirmationDialog();

            confirmationDialog.show(((MainActivity) mActivity).getFragmentManager(), "Alert Dialog Fragment");
        }catch (NullPointerException e){
            e.printStackTrace();
        }



    }

    public void MoveToPending(){
        dataBaseConnectionPresenter.getDbReference().child("PendingConfirm").
                child(givenUpShift.getShiftId()).setValue(((MainActivity)mActivity).getEmployee().getEmployeeId());
        dataBaseConnectionPresenter.getDbReference().child("GivenUpShifts").child(givenUpShift.getShiftId()).setValue(null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((MainActivity)mActivity).showFab();
    }
}
