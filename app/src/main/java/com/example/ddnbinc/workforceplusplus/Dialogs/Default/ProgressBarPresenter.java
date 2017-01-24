package com.example.ddnbinc.workforceplusplus.Dialogs.Default;

import android.app.Activity;

/**
 * Created by david on 2017-01-23.
 */

public class ProgressBarPresenter {
    private ProgressBarModel progressBarModel;

    public ProgressBarPresenter(Activity activity, String m){
        progressBarModel = new ProgressBarModel(activity,m);
    }
    public void Show(){
        progressBarModel.ShowProgressDialog();
    }
    public void Hide(){
        progressBarModel.HideProgressDialog();
    }

}
