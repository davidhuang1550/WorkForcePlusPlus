package com.example.ddnbinc.workforceplusplus.Fragments.Authentication.Login;

import android.app.Activity;

import com.example.ddnbinc.workforceplusplus.DataBaseConnection.DataBaseConnectionPresenter;
import com.example.ddnbinc.workforceplusplus.Dialogs.Default.ProgressBarPresenter;

/**
 * Created by david on 2017-01-23.
 */

public class LoginPresenter {
    private LoginModel loginModel;
    public LoginPresenter(String e, String p, DataBaseConnectionPresenter d, Activity activity, ProgressBarPresenter prog){
        loginModel = new LoginModel(e,p,d,activity,prog);
    }
    public void Login(){
        loginModel.Login();
    }
}
