package com.example.ddnbinc.workforceplusplus.Fragments.Authentication.SignUp;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ddnbinc.workforceplusplus.DataBaseConnection.DataBaseConnectionPresenter;
import com.example.ddnbinc.workforceplusplus.Dialogs.Default.ProgressBarPresenter;
import com.example.ddnbinc.workforceplusplus.R;

/**
 * Created by david on 2017-01-23.
 * this is temporary fragment going to be used for development phase. users are not allowed to create an account
 * the manager is the one creating the account.
 */

public class SignUp extends Fragment implements  View.OnClickListener{
    private View myView;
    private Activity mActivity;
    private String Email;
    private String Password;
    private String ConfirmPassword;
    private ProgressBarPresenter progressBarPresenter;
    private DataBaseConnectionPresenter dataBaseConnectionPresenter;
    private SignUpPresenter signUpPresenter;

    private  EditText et1;
    private  EditText et2;
    private  EditText et3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity=getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.signup_page,container,false);
        mActivity.setTitle("Sign Up");

        et1= (EditText)myView.findViewById(R.id.userName);
        et2 =(EditText)myView.findViewById(R.id.userPassword);
        et3 =(EditText)myView.findViewById(R.id.passwordConfirm);

        Button signup = (Button)myView.findViewById(R.id.signup);
        signup.setOnClickListener(this);



        return  myView;
    }

    public boolean checkpassword(){
        return (Password.equals(ConfirmPassword))?true:false;
    }

    public void onClick(View v) {
        if(v.getId()==R.id.signup) {
            Email= et1.getText().toString();
            Password= et2.getText().toString();
            ConfirmPassword=et3.getText().toString();

            if(checkpassword()) {
                progressBarPresenter = new ProgressBarPresenter(mActivity,"Creating Account...");
                progressBarPresenter.Show();
                signUpPresenter = new SignUpPresenter(Email,Password,dataBaseConnectionPresenter,progressBarPresenter,mActivity);
                signUpPresenter.CreateAccount();
            }else Toast.makeText(mActivity,"Password do not match",Toast.LENGTH_LONG).show();
        }
    }
    public void onDestroy() {
        ViewGroup container = (ViewGroup)mActivity.findViewById(R.id.content_frame);
        container.removeAllViews();
        super.onDestroy();
    }
}
