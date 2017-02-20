package com.example.ddnbinc.workforceplusplus.Dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.ddnbinc.workforceplusplus.MainActivity;
import com.example.ddnbinc.workforceplusplus.R;

/**
 * Created by davidhuang on 2017-01-27.
 */

public class ConfirmationDialog extends DialogFragment {
    private Activity mActivity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity=getActivity();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        final Bundle bundle =getArguments();


        if(bundle!=null){
            alertDialog.setTitle(bundle.getString("Title"));
            alertDialog.setMessage(bundle.getString("Message","Confirmation sent to manager"));
        }
        else{
            alertDialog.setMessage("Confirmation sent to manager");
        }

        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(bundle!=null) {
                        ((MainActivity) mActivity).onBackPressed();
                }
            }
        });
        return alertDialog.create();
    }
}
