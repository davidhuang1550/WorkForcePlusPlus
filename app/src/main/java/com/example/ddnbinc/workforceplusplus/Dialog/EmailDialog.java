package com.example.ddnbinc.workforceplusplus.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ddnbinc.workforceplusplus.Fragments.Email.SendEmail;
import com.example.ddnbinc.workforceplusplus.R;

import java.util.ArrayList;
import java.util.List;


public class EmailDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder optionDialog = new AlertDialog.Builder(getActivity());

        View view = View.inflate(getActivity(), R.layout.email_dialog,null);

        Button sendEmail = (Button)view.findViewById(R.id.SendEmail);
        Button viewEmail = (Button)view.findViewById(R.id.ViewEmail);

        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager =getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame,new SendEmail()).commit();

                dismiss();
            }
        });
        viewEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                startActivity(intent);
                startActivity(Intent.createChooser(intent, "View Email Via"));
                dismiss();
            }
        });



        optionDialog.setView(view);

        return optionDialog.create();

    }
}
