package com.example.ddnbinc.workforceplusplus;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ddnbinc.workforceplusplus.ChatRoom.ChatRoom;
import com.example.ddnbinc.workforceplusplus.DataBaseConnection.DataBaseConnectionPresenter;
import com.example.ddnbinc.workforceplusplus.Dialog.EmailDialog;
import com.example.ddnbinc.workforceplusplus.Dialogs.Default.ProgressBarPresenter;
import com.example.ddnbinc.workforceplusplus.Fragments.Authentication.Login.GetUserPresenter;
import com.example.ddnbinc.workforceplusplus.Fragments.Authentication.Login.Login;
import com.example.ddnbinc.workforceplusplus.Fragments.ProfileFragment;
import com.example.ddnbinc.workforceplusplus.Fragments.Shift.Shift;
import com.example.ddnbinc.workforceplusplus.Classes.Users.Employee;
import com.example.ddnbinc.workforceplusplus.Manager.PendingShifts;
import com.example.ddnbinc.workforceplusplus.Notifications.NotificationManager;
import com.example.ddnbinc.workforceplusplus.Notifications.NotificationTab;
import com.example.ddnbinc.workforceplusplus.Utilities.FabPresenter;
import com.example.ddnbinc.workforceplusplus.Utilities.ImageUploader;
import com.example.ddnbinc.workforceplusplus.Utilities.Settings;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

/*
 * This is mainly used for menu items ie, hide, show, inflate as well as a mediator for databaseconnetction
 * and fab presenter.
 */

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,FragmentManager.OnBackStackChangedListener {

    private DataBaseConnectionPresenter dataBaseConnectionPresenter;
    private Employee employee;
    private FabPresenter fabPresenter;
    private Menu tools;
    private MenuItem upload;
    private ConnectivityManager connectivityManager;
    private NetworkInfo activeNetworkInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        tools=navigationView.getMenu();

        FloatingActionButton floatingActionButton = (FloatingActionButton)findViewById(R.id.fab);
        fabPresenter = new FabPresenter(floatingActionButton,this);

        fabPresenter.Hide();


        dataBaseConnectionPresenter = DataBaseConnectionPresenter.getInstance();

        Bundle bundle = getIntent().getExtras();

       // System.out.println(System.currentTimeMillis()/1000);
        FragmentManager fragmentManager = getFragmentManager();
        if(bundle!=null){
            /*
            called when theres a notification
             */
            Bundle bundle1= bundle.getBundle("Info");
            if(bundle1!=null) {

                ProgressBarPresenter progressBarPresenter = new ProgressBarPresenter(this, "Loading");
                progressBarPresenter.Show();

                NotificationManager notificationManager = new NotificationManager(this, bundle1, progressBarPresenter);
                GetUserPresenter getUserPresenter = new GetUserPresenter(dataBaseConnectionPresenter, this, progressBarPresenter);
                getUserPresenter.setFlag(notificationManager);
                getUserPresenter.getUser();
            }else{
                fragmentManager.beginTransaction().add(R.id.content_frame,new Login()).commit();
            }

        }
        else{
            fragmentManager.beginTransaction().add(R.id.content_frame,new Login()).commit();
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    public DataBaseConnectionPresenter getDataBaseConnectionPresenter(){

        if (isNetworkAvailable()) return dataBaseConnectionPresenter;

        Snackbar.make(getCurrentFocus(), "No Internet Connection", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        return null;
    }

    public void setUserFcmToken(){
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.FCM_PREF), Context.MODE_PRIVATE);
        String token =sharedPreferences.getString(getString(R.string.FCM_TOKEN),"");

        if(!token.equals("")) dataBaseConnectionPresenter.getDbReference().child("Users").child(dataBaseConnectionPresenter.getUID()).
                child("fcmToken").setValue(token);

        //  Remove the preference token or else we would just keep calling this function every time we
        // log into the applcation
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(getString(R.string.FCM_PREF));
        editor.commit();
    }

    public void setEmployee(Employee e){
        employee=e;
        final ImageView imageView = (ImageView) findViewById(R.id.profileImage);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://workforceplusplus.appspot.com/Images/" +employee.getEmployeeId());
        storageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                try {
                    Picasso.with(getApplicationContext()).load(task.getResult()).fit().into(imageView);
                }catch(Exception e){
                    e.printStackTrace();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Failed to download image check Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void showFab(){
     fabPresenter.Show();
    }
    public void hideFab(){
        fabPresenter.Hide();
    }

    public void showUpload(){
        upload.setVisible(true);
    }

    public void hideUpload(){
        upload.setVisible(false);
    }

    public Employee getEmployee(){
        return employee;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        getMenuInflater().inflate(R.menu.main,menu);

        /*
         * not in use cases
         */
        MenuItem item = menu.findItem(R.id.action_settings);
        item.setVisible(false);
        tools.findItem(R.id.Chat).setVisible(false);


        upload = menu.getItem(0);

        upload.setVisible(false); // dont need this yet.

        return true;
    }


    public void hideStatusBar(){
        getSupportActionBar().hide();
    }
    public void showStatusBar(){
        getSupportActionBar().show();
    }

    public boolean isNetworkAvailable() {
        if(connectivityManager==null){
            connectivityManager
                    = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        }
        activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onBackStackChanged() {

        if (getFragmentManager().getBackStackEntryCount() > 0 ){

            getFragmentManager().popBackStack();

        } else {
            super.onBackPressed();
        }
    }

    public void hideManagerView(){
        tools.findItem(R.id.Pending).setVisible(false);// set logout and login respectively
    }
    public void showManagerView(){
        tools.findItem(R.id.Pending).setVisible(true);// set logout and login respectively
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            getFragmentManager().beginTransaction().replace(R.id.content_main,new Settings()).commit();

            return true;
        }else if(id== R.id.upload){
            try {
                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(galleryIntent, 2);
            }catch(RuntimeException e){e.printStackTrace();}
          //  Toast.makeText(this,"Upload selected", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK) {
            ImageUploader imageUploader = new ImageUploader(dataBaseConnectionPresenter,this);
            String path= imageUploader.getPath(this,data.getData());
            imageUploader.setFilePath(path);
            switch (requestCode) {
                case 1:
                    imageUploader.setStorageReference(R.string.PROFILE_IMAGE);
                    imageUploader.UploadImage();
                    break;
                case 2:
                    imageUploader.setStorageReference(R.string.CHAT_ROOM_IMAGE);
                    imageUploader.UploadImage();
                    break;
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            //resume tasks needing this permission
        }
    }
    public boolean isExternalStorageWritable() {
        String permission = "android.permission.WRITE_EXTERNAL_STORAGE"; // get permissions
        int res= this.checkCallingOrSelfPermission(permission);
        return (res== PackageManager.PERMISSION_GRANTED);
    }
    public boolean checkReadExternalPermission(){
        String permission = "android.permission.READ_EXTERNAL_STORAGE"; // get permissions
        int res= this.checkCallingOrSelfPermission(permission);
        return (res== PackageManager.PERMISSION_GRANTED);
    }
    public void requestForSpecificPermission() {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 101);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        FragmentManager fragmentManager = getFragmentManager();
        int id = item.getItemId();
        Bundle bundle = new Bundle();

        if (id == R.id.Profile) {
            fragmentManager .beginTransaction().replace(R.id.content_frame,new ProfileFragment(),"Shift").commit();
        } else if (id == R.id.Mail) {
            EmailDialog optionsDialog = new EmailDialog();
            optionsDialog.show(getFragmentManager(),"options Dialog");
        } else if (id == R.id.SwapShift) {
            bundle.putString("name", "Swap Shift");
            bundle.putBoolean("type",true);
            Shift shift = new Shift();
            shift.setArguments(bundle);
            fragmentManager .beginTransaction().replace(R.id.content_frame, shift,"Shift").commit();
        } else if (id == R.id.ViewShift) {
            bundle.putString("name","Your Shift");
            bundle.putBoolean("type",false);
            Shift shift = new Shift();
            shift.setArguments(bundle);
            fragmentManager .beginTransaction().replace(R.id.content_frame, shift).commit();
        } else if(id == R.id.Notifications){
            fragmentManager .beginTransaction().replace(R.id.content_frame,new NotificationTab()).commit();
        } else if (id == R.id.Logout) {
            FirebaseAuth.getInstance().signOut();
            freeUserData();
            fragmentManager.beginTransaction().replace(R.id.content_frame,new Login()).commit();
        }else if (id == R.id.Pending) {
            fragmentManager .beginTransaction().replace(R.id.content_frame,new PendingShifts(),"PendingShift").commit();
        }else if (id == R.id.Chat) {
            fragmentManager .beginTransaction().replace(R.id.content_frame,new ChatRoom()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void freeUserData(){
        ImageView imageView = (ImageView) findViewById(R.id.profileImage);
        imageView.setImageResource(R.drawable.default_profile);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FirebaseAuth.getInstance().signOut();
    }
}
