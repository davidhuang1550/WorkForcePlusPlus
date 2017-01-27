package com.example.ddnbinc.workforceplusplus.Fragments.Shift;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
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
        if(bundle!=null){
            init(bundle);
        }

        Button yes = (Button)myView.findViewById(R.id.yes);
        Button no = (Button)myView.findViewById(R.id.no);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);



        return myView;
    }
    public void init(Bundle b){
        TextView giver = (TextView)myView.findViewById(R.id.giver);
        TextView taker = (TextView)myView.findViewById(R.id.taker);
        TextView time = (TextView)myView.findViewById(R.id.time);

        employees[0] =(Employee)b.getSerializable("Giver");
        employees[1]= (Employee)b.getSerializable("Taker");


        giver.setText(employees[0].getEmail()+"Wants to give up a shift");
        taker.setText("And "+employees[1].getEmail()+" wants to take the shift");
        time.setText("At "+b.getString("Time"));
        givenUpShift = (Shifts) b.getSerializable("Shift");


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.yes:
                Accepted();
                setDisplay();
                break;
            case R.id.no:
                MoveToGivenUpShifts();
                break;
        }

    }
    public void setDisplay(){
        /*
        This will be replaced with a list of posted shift later.
         */


        FragmentManager fragmentManager = ((MainActivity)mActivity).getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame,new ViewShift()).commit();
    }

    public void MoveToGivenUpShifts(){
        destoryPending();
        AddToGivenUp();

    }
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
        dataBaseConnectionPresenter.getDbReference().child("Users")
                .child(employees[0].getEmployeeId()).child("Shifts").child(givenUpShift.getShiftId()).setValue(null);

        dataBaseConnectionPresenter.getDbReference().child("Users").child(employees[1].getEmployeeId())
                .child("Shifts").child(givenUpShift.getShiftId()).setValue(0);
    }

    public void AddToGivenUp(){
        GivenUpShift givenUp = new GivenUpShift();
        givenUp.setEndTime(givenUpShift.getEndTime());
        givenUp.setStartTime(givenUpShift.getStartTime());

        dataBaseConnectionPresenter.getDbReference().child("GivenUpShifts").child(givenUpShift.getShiftId()).setValue(givenUp);

    }
}