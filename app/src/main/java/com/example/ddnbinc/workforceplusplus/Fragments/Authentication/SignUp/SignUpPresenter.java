package com.example.ddnbinc.workforceplusplus.Fragments.Authentication.SignUp;

import android.app.Activity;

import com.example.ddnbinc.workforceplusplus.DataBaseConnection.DataBaseConnectionPresenter;
import com.example.ddnbinc.workforceplusplus.Dialogs.Default.ProgressBarPresenter;

/**
 * Created by david on 2017-01-23.
 */

public class SignUpPresenter {
    private SignUpModel signUpModel;
    public SignUpPresenter(String e, String p, DataBaseConnectionPresenter d, ProgressBarPresenter prog, Activity activity) {
        signUpModel = new SignUpModel(e,p,d,prog,activity);
    }
    public void CreateAccount(){
        signUpModel.createNewAccount();
    }

}
