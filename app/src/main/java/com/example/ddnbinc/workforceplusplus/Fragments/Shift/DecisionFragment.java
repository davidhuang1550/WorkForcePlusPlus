package com.example.ddnbinc.workforceplusplus.Fragments.Shift;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.sax.RootElement;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ddnbinc.workforceplusplus.Classes.GivenUpShift;
import com.example.ddnbinc.workforceplusplus.Classes.Shifts;
import com.example.ddnbinc.workforceplusplus.Classes.Users.Employee;
import com.example.ddnbinc.workforceplusplus.DataBaseConnection.DataBaseConnectionPresenter;
import com.example.ddnbinc.workforceplusplus.Fragments.Shift.ViewShift.ViewShift;
import com.example.ddnbinc.workforceplusplus.MainActivity;
import com.example.ddnbinc.workforceplusplus.Notifications.SendNotification;
import com.example.ddnbinc.workforceplusplus.R;

/**
 * Created by davidhuang on 2017-01-26.
 */

public class DecisionFragment extends Fragment implements  View.OnClickListener {

    private Activity mActivity;
    private DataBaseConnectionPresenter dataBaseConnectionPresenter;
    private View myView;
    private Shifts givenUpShift;
    private Employee[] employees;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity=getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.decision_view, container,false);
        dataBaseConnectionPresenter=((MainActivity)mActivity).getDataBaseConnectionPresenter();
        employees= new Employee[2];

        Bundle bundle = getArguments();
        if(bundle!=null && dataBaseConnectionPresenter!=null){
            init(bundle);
        }

        Button yes = (Button)myView.findViewById(R.id.yes);
        Button no = (Button)myView.findViewById(R.id.no);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);



        return myView;
    }
    public void init(Bundle b){
        try {
            TextView giver = (TextView) myView.findViewById(R.id.giver);
            TextView taker = (TextView) myView.findViewById(R.id.taker);
            TextView time = (TextView) myView.findViewById(R.id.time);

            employees[0] = (Employee) b.getParcelable("Giver");
            employees[1] = (Employee) b.getParcelable("Taker");


            giver.setText(employees[0].getEmail());
            taker.setText(employees[1].getEmail());
            time.setText(b.getString("Time"));
            givenUpShift = (Shifts) b.getSerializable("Shift");
        }catch (RuntimeException e){
            e.printStackTrace();
        }


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.yes:
                Accepted();
                NotifyEmployees("Approved");
                setDisplay();
                break;
            case R.id.no:
                MoveToGivenUpShifts();
                NotifyEmployees("Denied");
                setDisplay();
                break;
        }

    }
    public void NotifyEmployees(String Response){
        SendNotification sendNotification_emp1 = new SendNotification(mActivity,givenUpShift.getShiftId(), Response,employees[0].getFcmToken());
        sendNotification_emp1.sendToken();

        SendNotification sendNotification_emp2 = new SendNotification(mActivity,givenUpShift.getShiftId(), Response,employees[1].getFcmToken());
        sendNotification_emp2.sendToken();

    }

    public void setDisplay(){
        /*
        This will be replaced with a list of posted shift later.
         */


        FragmentManager fragmentManager = ((MainActivity)mActivity).getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame,new ViewShift()).commit();
    }
    /*
    shift trade denied move the pending shift back to given up shift for someone else to take
     */
    public void MoveToGivenUpShifts(){
        destoryPending();
        AddToGivenUp();

    }
    /*
    remove from pending and given up and then swap the shifts with the two employees
     */
    public void Accepted(){
        destoryPending();
        destoryGivenUp();
        swap();
        // move between two employees
    }
    public void destoryPending(){
        dataBaseConnectionPresenter.getDbReference().child("PendingConfirm").child(givenUpShift.getShiftId()).setValue(null);
    }
    public void destoryGivenUp(){
        dataBaseConnectionPresenter.getDbReference().child("GivenUpShifts").child(givenUpShift.getShiftId()).setValue(null);

    }
    public void swap(){
        GivenUpShift givenUp = new GivenUpShift();
        givenUp.setStartTime(givenUpShift.getStartTime());
        givenUp.setEndTime(givenUpShift.getEndTime());
        dataBaseConnectionPresenter.getDbReference().child("Users")
                .child(employees[0].getEmployeeId()).child("Shifts").child(givenUpShift.getShiftId()).setValue(null);

        dataBaseConnectionPresenter.getDbReference().child("Users").child(employees[1].getEmployeeId())
                .child("Shifts").child(givenUpShift.getShiftId()).setValue(givenUp);
    }

    public void AddToGivenUp(){
        GivenUpShift givenUp = new GivenUpShift();
        givenUp.setEndTime(givenUpShift.getEndTime());
        givenUp.setStartTime(givenUpShift.getStartTime());

        dataBaseConnectionPresenter.getDbReference().child("GivenUpShifts").child(givenUpShift.getShiftId()).setValue(givenUp);

    }
}
