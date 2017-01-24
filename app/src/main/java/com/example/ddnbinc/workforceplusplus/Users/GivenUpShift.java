package com.example.ddnbinc.workforceplusplus.Users;

/**
 * Created by david on 2017-01-23.
 */

public class GivenUpShift {

    private String EmployeeId;
    private Long StartTime;
    private Long EndTime;
    private String ShiftId;
    public GivenUpShift(){

    }
    public GivenUpShift(String e,Long start, Long end,String Shift){
        EmployeeId=e;
        StartTime=start;
        EndTime=end;
        ShiftId=Shift;
    }
    public String getEmployeeId() {
        return EmployeeId;
    }

    public void setEmployeeId(String employeeId) {
        EmployeeId = employeeId;
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
