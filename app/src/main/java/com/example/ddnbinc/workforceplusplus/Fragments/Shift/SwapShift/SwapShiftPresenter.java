package com.example.ddnbinc.workforceplusplus.Fragments.Shift.SwapShift;

import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.example.ddnbinc.workforceplusplus.DataBaseConnection.DataBaseConnectionPresenter;
import com.example.ddnbinc.workforceplusplus.Classes.GivenUpShift;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by david on 2017-01-23.
 */

public class SwapShiftPresenter {
    private SwapShiftModel swapShiftModel;


    public SwapShiftPresenter(Activity a, DataBaseConnectionPresenter data, SwipeRefreshLayout swipe,View v,View main){
        swapShiftModel = new SwapShiftModel(a,data,swipe,v,main);
    }

    public void init(){
        swapShiftModel.init();
    }
    public HashMap<String,ArrayList<GivenUpShift>> getWeekShift(){
       return swapShiftModel.getWeekShift();
    }
    public void setValues(HashMap<String,ArrayList<GivenUpShift>> shifts){
        swapShiftModel.setValues(shifts);
    }
    public Long getTime(){
        return swapShiftModel.getEndTime();
    }
    public void setTime(Long time){
        swapShiftModel.setEndTime(time);
    }
    public void next_week(){
        swapShiftModel.next_init();
    }

}
