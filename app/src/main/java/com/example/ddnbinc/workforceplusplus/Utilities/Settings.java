package com.example.ddnbinc.workforceplusplus.Utilities;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.ddnbinc.workforceplusplus.MainActivity;
import com.example.ddnbinc.workforceplusplus.R;

/**
 * Created by davidhuang on 2017-04-03.
 * WE MUST NOW USE THE OBSERVER PATTERN HERE.
 */

public class Settings extends Fragment {

    private Activity mActivity;
    private View mView;

    private Switch.OnCheckedChangeListener switchListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


            switch (compoundButton.getId()){
                case R.id.resethint:
                    saveSettings(R.string.RESET_HINT,(b) ? R.string.HINT_RESET_TRUE : R.string.HINT_RESET_FALSE);

                    break;
                case R.id.theme:
                    saveSettings(R.string.THEME,(b) ? R.string.DARK_THEME : R.string.LIGHT_THEME);

                    break;
                case R.id.enable_notifications:
                    saveSettings(R.string.NOTIFICATIONS_STATUS,(b) ? R.string.NOTIFICATIONS_ENABLED : R.string.NOTIFICATIONS_DISABLED);
                    break;
            }
        }

    };


    private TextView.OnClickListener textListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.resetPassword:
                    //  ((MainActivity)mActivity).getDataBaseConnectionPresenter().getFirebaseUser().updatePassword("");
                    //
                    break;
                case R.id.changeName:
                    //
                    break;
                case R.id.hideAds:
                    //
                    break;
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }


    /*
     * Some of these may be dynamically removed depending on the account
     * settings so they may also be dynamically added (ie. Hide Ads may be hidden or not added
     * if the user has payed for the premium version.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.settings_page,container,false);


        // Switches APP settings

        Switch _resetHint = (Switch)mView.findViewById(R.id.resethint);
        Switch _theme = (Switch)mView.findViewById(R.id.theme);
        Switch _enableNotifications = (Switch)mView.findViewById(R.id.enable_notifications);

        // Account Settings

        TextView _resetPassword = (TextView)mView.findViewById(R.id.resetPassword);
        // change screen name
        TextView _changeName = (TextView)mView.findViewById(R.id.changeName);
        TextView _hideAds = (TextView)mView.findViewById(R.id.hideAds);

        _resetPassword.setOnClickListener(textListener);
        _changeName.setOnClickListener(textListener);
        _hideAds.setOnClickListener(textListener);


        _resetHint.setOnCheckedChangeListener(switchListener);
        _theme.setOnCheckedChangeListener(switchListener);
        _enableNotifications.setOnCheckedChangeListener(switchListener);

        return mView;
    }

    private void saveSettings(int setting, int type){
        SharedPreferences sharedPreferences = mActivity.getApplicationContext().getSharedPreferences(getString(R.string.FCM_PREF), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.commit();

    }

}
