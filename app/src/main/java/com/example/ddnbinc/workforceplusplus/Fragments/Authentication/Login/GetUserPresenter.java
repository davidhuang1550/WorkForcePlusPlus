package com.example.ddnbinc.workforceplusplus.Fragments.Authentication.Login;

import android.app.Activity;

import com.example.ddnbinc.workforceplusplus.DataBaseConnection.DataBaseConnectionPresenter;
import com.example.ddnbinc.workforceplusplus.Dialogs.Default.ProgressBarPresenter;

/**
 * Created by david on 2017-01-23.
 */

public class GetUserPresenter {
    private GetUserModel getUserModel;
    public GetUserPresenter(DataBaseConnectionPresenter dataBase, Activity activity, ProgressBarPresenter prog){
        getUserModel = new GetUserModel(dataBase,activity,prog);
    }
    public void getUser(){
        getUserModel.getUser();
    }
}
