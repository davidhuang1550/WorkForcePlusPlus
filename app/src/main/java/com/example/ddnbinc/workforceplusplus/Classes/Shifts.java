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

    /**
     *
     * @param e employee id
     * @param start shift start time
     * @param end shift end time
     * @param Shift id of this current object
     */
    public Shifts(String e,Long start, Long end,String Shift){
        employeeid=e;
        StartTime=start;
        EndTime=end;
        ShiftId=Shift;
    }

    /**
     *
     * @return
     */
    public String getEmployeeid() {
        return employeeid;
    }

    /**
     *
     * @param employeeId
     */
    public void setEmployeeid(String employeeId) {
        employeeid = employeeId;
    }

    /**
     *
     * @return
     */
    public Long getStartTime() {
        return StartTime;
    }

    /**
     *
     * @param startTime
     */
    public void setStartTime(Long startTime) {
        StartTime = startTime;
    }

    /**
     *
     * @return
     */
    public Long getEndTime() {
        return EndTime;
    }

    /**
     *
     * @param endTime
     */
    public void setEndTime(Long endTime) {
        EndTime = endTime;
    }

    /**
     *
     * @return
     */
    public String getShiftId() {
        return ShiftId;
    }

    /**
     *
     * @param shiftId
     */
    public void setShiftId(String shiftId) {
        ShiftId = shiftId;
    }



}
