package com.example.ddnbinc.workforceplusplus.Fragments.Authentication.Login;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.ddnbinc.workforceplusplus.DataBaseConnection.DataBaseConnectionPresenter;
import com.example.ddnbinc.workforceplusplus.Dialogs.Default.ProgressBarPresenter;
import com.example.ddnbinc.workforceplusplus.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

/**
 * Created by david on 2017-01-23.
 */

public class LoginModel {
    private String Email;
    private String Password;
    private DataBaseConnectionPresenter dataBaseConnectionPresenter;
    private Activity mActivity;
    private ProgressBarPresenter progressBarPresenter;
    public LoginModel(String e, String p, DataBaseConnectionPresenter d, Activity activity, ProgressBarPresenter prog){
        Email=e;
        Password=p;
        dataBaseConnectionPresenter=d;
        mActivity=activity;
        progressBarPresenter=prog;
    }
    protected void Login(){
        dataBaseConnectionPresenter = ((MainActivity)mActivity).getDataBaseConnectionPresenter();
        dataBaseConnectionPresenter.getFireBaseAuth().signInWithEmailAndPassword(Email,Password).addOnCompleteListener(mActivity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!(task.isSuccessful())){
                    Toast.makeText(mActivity,"authentication failed",Toast.LENGTH_SHORT).show();
                }
                else{

                    dataBaseConnectionPresenter.setFirebaseUser();

                    GetUserPresenter getUserAsyncTask = new GetUserPresenter(dataBaseConnectionPresenter,mActivity,progressBarPresenter);
                    getUserAsyncTask.getUser();

                }
            }
        });
    }
}

