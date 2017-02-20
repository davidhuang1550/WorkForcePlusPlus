package com.example.ddnbinc.workforceplusplus.Fragments.Authentication;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ddnbinc.workforceplusplus.Classes.Users.Employee;
import com.example.ddnbinc.workforceplusplus.MainActivity;
import com.example.ddnbinc.workforceplusplus.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

/**
 * Created by davidhuang on 2017-02-05.
 */

public class ProfileFragment extends Fragment implements View.OnClickListener{
    private View myView;
    private Activity mActivity;
    private Employee employee;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mActivity=getActivity();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.profile_view,container,false);

        mActivity.setTitle("Profile");

        TextView email = (TextView)myView.findViewById(R.id.Email);
        employee = ((MainActivity)mActivity).getEmployee();
        Button upload = (Button)myView.findViewById(R.id.upload);
        upload.setOnClickListener(this);
         final ImageView imageView = (ImageView) myView.findViewById(R.id.profileImage);
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReferenceFromUrl("gs://workforceplusplus.appspot.com/Images/" +employee.getEmployeeId());
            storageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    try {
                        Picasso.with(mActivity).load(task.getResult()).fit().into(imageView);
                    }catch(Exception e){
                        e.printStackTrace();
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(mActivity, "Failed to download image", Toast.LENGTH_SHORT).show();
                }
            });




        email.setText(employee.getEmail());

        return myView;
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.upload){
            if(((MainActivity)mActivity).checkReadExternalPermission()) {
                try {
                    final Intent galleryIntent = new Intent();
                    galleryIntent.setType("image/*");
                    galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                    mActivity.startActivityForResult(galleryIntent, 1);
                }catch(RuntimeException e){e.printStackTrace();}
            }else{((MainActivity)mActivity).requestForSpecificPermission();}
        }
    }
}
