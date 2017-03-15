package com.example.ddnbinc.workforceplusplus.Fragments.Authentication.Login;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ddnbinc.workforceplusplus.DataBaseConnection.DataBaseConnectionPresenter;
import com.example.ddnbinc.workforceplusplus.Dialogs.Default.ProgressBarPresenter;
import com.example.ddnbinc.workforceplusplus.Fragments.Authentication.SignUp.SignUp;
import com.example.ddnbinc.workforceplusplus.MainActivity;
import com.example.ddnbinc.workforceplusplus.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

/**
 * Created by david on 2017-01-23.
 */

public class Login extends Fragment  implements View.OnClickListener{
    private View myView;
    private Activity mActivity;
    private String Email;
    private String Password;
    private DataBaseConnectionPresenter dataBaseConnectionPresenter;
    private ProgressBarPresenter progressBarPresenter;
    private LoginPresenter loginPresenter;
    private EditText et1;
    private EditText et2;
    private CheckBox rememberMe;
    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity= getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView= inflater.inflate(R.layout.login_page,container,false);
        mActivity.setTitle("Log In");

        ((MainActivity)mActivity).hideStatusBar();

        et1 = (EditText)myView.findViewById(R.id.userName);
        et2 = (EditText)myView.findViewById(R.id.userPassword);

        Button signin = (Button)myView.findViewById(R.id.signIn);
        rememberMe = (CheckBox)myView.findViewById(R.id.remember_me);

        sharedPreferences = ((MainActivity)mActivity).getSharedPreferences(getString(R.string.CREDENTIALS), Context.MODE_PRIVATE);
        if(sharedPreferences!=null){
            rememberMe.setChecked(true);
            _retrieveCredentials(et1,et2,sharedPreferences);
        }

        signin.setOnClickListener(this);





        return myView;
    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signIn:

                Email=et1.getText().toString();
                Password = et2.getText().toString();

                progressBarPresenter = new ProgressBarPresenter(mActivity,"Loging In...");
                progressBarPresenter.Show();
                loginPresenter = new LoginPresenter(Email,Password,dataBaseConnectionPresenter,mActivity,progressBarPresenter);
                if(rememberMe.isChecked())_storeCredentials(Email,Password);
                else{ destroyPreferences(sharedPreferences);}
                loginPresenter.Login();

                break;
            case R.id.signup:
                getFragmentManager().beginTransaction().replace(R.id.content_frame, new SignUp()).commit();
                break;
        }

    }

    public void onDestroy() {
        ViewGroup container = (ViewGroup)mActivity.findViewById(R.id.content_frame);
        container.removeAllViews();
        super.onDestroy();
    }
    /*
     * http://stackoverflow.com/questions/9233035/best-option-to-store-username-and-password-in-android-app
     * refer to the above link when we are ready to provide encryption to our password.
     */
    public void _storeCredentials(String username, String password){
        SharedPreferences sharedPreferences = ((MainActivity)mActivity).getApplicationContext().getSharedPreferences(getString(R.string.CREDENTIALS), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.USERNAME),username);
        editor.putString(getString(R.string.PASSWORD),password);
        editor.commit();

    }

    public void _retrieveCredentials(EditText username, EditText password,SharedPreferences sharedPreferences){
        username.setText(sharedPreferences.getString(getString(R.string.USERNAME),""));
        password.setText(sharedPreferences.getString(getString(R.string.PASSWORD),""));
    }

    public void destroyPreferences(SharedPreferences sharedPreferences){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(getString(R.string.CREDENTIALS));
        editor.commit();
    }

}
