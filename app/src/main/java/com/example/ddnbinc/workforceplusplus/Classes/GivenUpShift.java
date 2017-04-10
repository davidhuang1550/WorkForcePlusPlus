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

    /**
     * @param start the start time of the shift
     * @param end the end time of the shift
     * @param Shift the shift id reference
     */
    public GivenUpShift(Long start, Long end,String Shift){
        StartTime=start;
        EndTime=end;
        ShiftId=Shift;
    }

    /**
     *
     * @return the start time
     */
    public Long getStartTime() {
        return StartTime;
    }

    /**
     * @param startTime set the start time generally used by the firebase to load data
     */
    public void setStartTime(Long startTime) {
        StartTime = startTime;
    }

    /**
     *
     * @return the end time
     */
    public Long getEndTime() {
        return EndTime;
    }

    /**
     *
     * @param endTime the end time
     */
    public void setEndTime(Long endTime) {
        EndTime = endTime;
    }

    /**
     *
     * @return shift id reference used by the firebase api
     */
    public String getShiftId() {
        return ShiftId;
    }

    /**
     *
     * @param shiftId set the shift id reference
     */
    public void setShiftId(String shiftId) {
        ShiftId = shiftId;
    }

}
