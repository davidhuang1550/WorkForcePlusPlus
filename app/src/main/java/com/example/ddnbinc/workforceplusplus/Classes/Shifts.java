package com.example.ddnbinc.workforceplusplus.Classes;

import java.io.Serializable;

/**
 * Created by davidhuang on 2017-01-26.
 */

public class Shifts implements Serializable {

    private String employeeid;
    private Long StartTime;
    private Long EndTime;
    private String ShiftId;
    public Shifts(){

    }
    public Shifts(String e,Long start, Long end,String Shift){
        employeeid=e;
        StartTime=start;
        EndTime=end;
        ShiftId=Shift;
    }
    public String getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(String employeeId) {
        employeeid = employeeId;
    }

    public Long getStartTime() {
        return StartTime;
    }

    public void setStartTime(Long startTime) {
        StartTime = startTime;
    }

    public Long getEndTime() {
        return EndTime;
    }

    public void setEndTime(Long endTime) {
        EndTime = endTime;
    }

    public String getShiftId() {
        return ShiftId;
    }

    public void setShiftId(String shiftId) {
        ShiftId = shiftId;
    }



}
