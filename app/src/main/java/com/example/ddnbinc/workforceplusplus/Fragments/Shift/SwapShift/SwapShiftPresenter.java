package com.example.ddnbinc.workforceplusplus.Fragments.Shift.SwapShift;

import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.example.ddnbinc.workforceplusplus.DataBaseConnection.DataBaseConnectionPresenter;

/**
 * Created by david on 2017-01-23.
 */

public class SwapShiftPresenter {
    private SwapShiftModel swapShiftModel;


    public SwapShiftPresenter(Activity a, DataBaseConnectionPresenter data, SwipeRefreshLayout swipe,View v){
        swapShiftModel = new SwapShiftModel(a,data,swipe,v);
    }

    public void init(){
        swapShiftModel.init();
    }

}
