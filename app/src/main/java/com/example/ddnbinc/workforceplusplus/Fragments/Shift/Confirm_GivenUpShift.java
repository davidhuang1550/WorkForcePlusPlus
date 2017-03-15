package com.example.ddnbinc.workforceplusplus.Fragments.Shift;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ddnbinc.workforceplusplus.Classes.GivenUpShift;
import com.example.ddnbinc.workforceplusplus.Classes.Users.Manager;
import com.example.ddnbinc.workforceplusplus.DataBaseConnection.DataBaseConnectionPresenter;
import com.example.ddnbinc.workforceplusplus.Dialog.ConfirmationDialog;
import com.example.ddnbinc.workforceplusplus.MainActivity;
import com.example.ddnbinc.workforceplusplus.Notifications.SendNotification;
import com.example.ddnbinc.workforceplusplus.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by davidhuang on 2017-01-25.
 */

public class Confirm_GivenUpShift extends Fragment implements  View.OnClickListener{
    private View myView;
    private GivenUpShift givenUpShift;
    private TextView description;
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

        description = (TextView)myView.findViewById(R.id.swap_shift_info);

        if(bundle!=null){
            givenUpShift= (GivenUpShift) bundle.getSerializable("GivenUpShift");
            description.setText(bundle.getString("Times")+"\n Are you sure you want to take this shift?");

        }
        Button yes = (Button)myView.findViewById(R.id.yes);
        Button no = (Button)myView.findViewById(R.id.no);

        yes.setOnClickListener(this);
        no.setOnClickListener(this);



        return myView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.yes:
                sendNotification();


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
                    String fcm = dataSnapshot.getValue(String.class);

                    SendNotification sendNotification = new SendNotification(mActivity, givenUpShift.getShiftId(), ((MainActivity) mActivity)
                            .getEmployee().getEmployeeId(), fcm);

                    MoveToPending();
                    sendNotification.sendToken();
                    ConfirmationDialog confirmationDialog = new ConfirmationDialog();

                    confirmationDialog.show(((MainActivity) mActivity).getFragmentManager(), "Alert Dialog Fragment");

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
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
