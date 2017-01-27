package com.example.ddnbinc.workforceplusplus.Fragments.Authentication.SignUp;

import android.app.Activity;
import android.app.FragmentManager;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.ddnbinc.workforceplusplus.DataBaseConnection.DataBaseConnectionPresenter;
import com.example.ddnbinc.workforceplusplus.Dialogs.Default.ProgressBarPresenter;
import com.example.ddnbinc.workforceplusplus.Fragments.Authentication.Login.Login;
import com.example.ddnbinc.workforceplusplus.MainActivity;
import com.example.ddnbinc.workforceplusplus.R;
import com.example.ddnbinc.workforceplusplus.Classes.Users.Manager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.iid.FirebaseInstanceId;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by david on 2017-01-23.
 */

public class SignUpModel {
    private String Email;
    private String Password;
    private DataBaseConnectionPresenter dataBaseConnectionPresenter;
    private ProgressBarPresenter progressBarPresenter;
    private Activity mActivity;


    public SignUpModel(String e, String p, DataBaseConnectionPresenter d, ProgressBarPresenter prog, Activity activity){
        Email=e;
        Password=p;
        dataBaseConnectionPresenter=d;
        progressBarPresenter=prog;
        mActivity=activity;
    }

    protected void createNewAccount(){
        dataBaseConnectionPresenter = ((MainActivity)mActivity).getDataBaseConnectionPresenter();
        dataBaseConnectionPresenter.getFireBaseAuth().createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(mActivity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBarPresenter.Hide();
                if(!(task.isSuccessful())){
                    Toast.makeText(mActivity,"Something went wrong",Toast.LENGTH_SHORT).show();
                    System.out.println(task.getException().toString());
                }
                else{
                    Date cDate = new Date();
                    String fDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);

                    Manager manager = new Manager( FirebaseInstanceId.getInstance().getToken(),fDate,Email,Password,task.getResult().getUser().getUid(),"E");

                    dataBaseConnectionPresenter.getDbReference().child("Users").child(task.getResult().getUser().getUid()).setValue(manager);

                    FragmentManager fragmentManager = ((MainActivity)mActivity).getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_frame,new Login()).commit();

                }
            }
        });
    }
}
