package com.example.ddnbinc.workforceplusplus.Classes;

import java.io.Serializable;

/**
 * Created by david on 2017-01-23.
 */

public class GivenUpShift implements Serializable {

    private Long StartTime;
    private Long EndTime;
    private String ShiftId;
    public GivenUpShift(){

    }
    public GivenUpShift(Long start, Long end,String Shift){
        StartTime=start;
        EndTime=end;
        ShiftId=Shift;
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
