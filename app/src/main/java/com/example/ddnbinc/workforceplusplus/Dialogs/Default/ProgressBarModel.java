package com.example.ddnbinc.workforceplusplus.Dialogs.Default;

import android.app.Activity;
import android.app.ProgressDialog;

/**
 * Created by david on 2017-01-23.
 */

public class ProgressBarModel {
    private ProgressDialog pDialog;
    private Activity mActivity;
    private String Message;
    public ProgressBarModel(Activity activity,String m){
        Message=m;
        mActivity=activity;
    }

    public void ShowProgressDialog() { // progress
        if (pDialog == null) {
            pDialog = new ProgressDialog(mActivity);
            pDialog.setMessage(Message);
            pDialog.setIndeterminate(true);
        }
        pDialog.show();
    }
    public void HideProgressDialog() {
        if(pDialog!=null && pDialog.isShowing()){
            pDialog.dismiss();
            pDialog.cancel();
        }
    }
}
