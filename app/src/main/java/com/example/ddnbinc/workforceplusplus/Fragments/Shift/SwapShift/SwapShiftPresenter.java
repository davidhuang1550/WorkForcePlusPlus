package com.example.ddnbinc.workforceplusplus.Fragments.Shift.SwapShift;

import android.app.Activity;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.example.ddnbinc.workforceplusplus.DataBaseConnection.DataBaseConnectionPresenter;
import com.example.ddnbinc.workforceplusplus.Classes.GivenUpShift;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by david on 2017-01-23.
 */

public class SwapShiftPresenter implements Serializable,Parcelable {
    private SwapShiftModel swapShiftModel;

    public SwapShiftPresenter(){

    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
    }

    public static final Parcelable.Creator<SwapShiftPresenter> CREATOR
            = new Parcelable.Creator<SwapShiftPresenter>() {
        public SwapShiftPresenter createFromParcel(Parcel in) {
            return new SwapShiftPresenter();
        }

        public SwapShiftPresenter[] newArray(int size) {
            return new SwapShiftPresenter[size];
        }
    };

    @Override
    public int describeContents(){
        return 0;
    }



    /*public SwapShiftPresenter(Activity a, DataBaseConnectionPresenter data, SwipeRefreshLayout swipe,View v,View main){
        swapShiftModel = new SwapShiftModel(a,data,swipe,v,main);
    }*/
    public SwapShiftPresenter(Activity a, SwipeRefreshLayout swipe,View main){
        swapShiftModel = new SwapShiftModel(a,swipe,main);
    }

    public void init(String e){
        swapShiftModel.init(e);
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
    //public void next_week(String s){
       // swapShiftModel.next_init(s);
   // }
    public void setDatabase(DataBaseConnectionPresenter data){
        swapShiftModel.setDatabase(data);
    }
    public void setView(View view){
        swapShiftModel.setView(view);
    }
    public void setClickable(boolean b){
        swapShiftModel.setClickable(b);
    }
    public void setDateHeader(){
        swapShiftModel.setDateHeader();
    }

}
