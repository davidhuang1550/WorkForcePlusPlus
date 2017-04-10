package com.example.ddnbinc.workforceplusplus.Manager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * Created by davidhuang on 2017-02-19.
 */

public class ShiftInfoPresenter {
    private ShiftInfoModel shiftInfoModel;

    public ShiftInfoPresenter(Activity activity,String t1, String t2){
        shiftInfoModel= new ShiftInfoModel(activity,t1,t2);
    }
    public void setView(){
        shiftInfoModel.setView();
    }

}
