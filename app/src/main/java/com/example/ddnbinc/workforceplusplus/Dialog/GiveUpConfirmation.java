package com.example.ddnbinc.workforceplusplus.Dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import com.example.ddnbinc.workforceplusplus.Classes.GivenUpShift;
import com.example.ddnbinc.workforceplusplus.Classes.Users.Employee;
import com.example.ddnbinc.workforceplusplus.DataBaseConnection.DataBaseConnectionPresenter;
import com.example.ddnbinc.workforceplusplus.MainActivity;
import com.example.ddnbinc.workforceplusplus.ShiftManager;

/**
 * Created by davidhuang on 2017-01-28.
 */

public class GiveUpConfirmation extends DialogFragment {
    private Activity mActivity;
    private DataBaseConnectionPresenter dataBaseConnectionPresenter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity=getActivity();
        dataBaseConnectionPresenter=((MainActivity)mActivity).getDataBaseConnectionPresenter();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        final Bundle bundle =getArguments();



            alertDialog.setTitle("Give up shift.");
            alertDialog.setMessage("Are you sure you want to give up this shift");
//    public ShiftManager(DataBaseConnectionPresenter d,GivenUpShift g,Employee[] e){

        alertDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(bundle!=null){
                    ShiftManager shiftManager = new ShiftManager(dataBaseConnectionPresenter, (GivenUpShift) bundle.getSerializable("Shift"));
                    shiftManager.AddToGivenUp();

                    setShowsDialog(false);

                    bundle.putString("Title","Shift Given Up");
                    bundle.putString("Message","Shift is given up");
                    bundle.putBoolean("IsBack",false);
                    ConfirmationDialog confirmationDialog = new ConfirmationDialog();
                    confirmationDialog.setArguments(bundle);
                    confirmationDialog.show(((MainActivity)mActivity).getFragmentManager(),"Tag");


                }else{
                    Toast.makeText(mActivity,"Something went wrong",Toast.LENGTH_SHORT).show();
                }
            }
        });

        alertDialog.setNegativeButton("Cancel",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setShowsDialog(false);
                dismiss();
            }
        });
        return alertDialog.create();
    }
}
