package com.example.ddnbinc.workforceplusplus.Utilities;

import android.app.Activity;
import android.support.design.widget.FloatingActionButton;

/**
 * Created by davidhuang on 2017-01-28.
 */

public class FabPresenter {
    private FabModel fabModel;

    public FabPresenter(FloatingActionButton f, Activity activity){
        fabModel= new FabModel(f,activity);
    }
    public void Show(){
     fabModel.Show();
    }
    public void Hide(){
        fabModel.Hide();
    }


}
