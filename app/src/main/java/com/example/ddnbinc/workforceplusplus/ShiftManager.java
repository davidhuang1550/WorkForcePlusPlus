package com.example.ddnbinc.workforceplusplus;

import com.example.ddnbinc.workforceplusplus.Classes.GivenUpShift;
import com.example.ddnbinc.workforceplusplus.Classes.Users.Employee;
import com.example.ddnbinc.workforceplusplus.DataBaseConnection.DataBaseConnectionPresenter;

/**
 * Created by davidhuang on 2017-01-28.
 */

public class ShiftManager {
    private DataBaseConnectionPresenter dataBaseConnectionPresenter;
    private GivenUpShift givenUpShift;
    private Employee[] employees;

    public ShiftManager(DataBaseConnectionPresenter d,GivenUpShift g,Employee[] e){
        dataBaseConnectionPresenter=d;
        givenUpShift=g;
        employees=e;
    }
    public ShiftManager(DataBaseConnectionPresenter d,GivenUpShift g){
        dataBaseConnectionPresenter=d;
        givenUpShift=g;
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
