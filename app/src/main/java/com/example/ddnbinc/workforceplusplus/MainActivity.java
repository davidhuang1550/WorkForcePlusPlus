package com.example.ddnbinc.workforceplusplus;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ddnbinc.workforceplusplus.DataBaseConnection.DataBaseConnectionPresenter;
import com.example.ddnbinc.workforceplusplus.Dialog.EmailDialog;
import com.example.ddnbinc.workforceplusplus.Dialogs.Default.ProgressBarPresenter;
import com.example.ddnbinc.workforceplusplus.Fragments.Authentication.Login.GetUserPresenter;
import com.example.ddnbinc.workforceplusplus.Fragments.Authentication.Login.Login;
import com.example.ddnbinc.workforceplusplus.Fragments.Authentication.ProfileFragment;
import com.example.ddnbinc.workforceplusplus.Fragments.Shift.Shift;
import com.example.ddnbinc.workforceplusplus.Classes.Users.Employee;
import com.example.ddnbinc.workforceplusplus.Manager.PendingShifts;
import com.example.ddnbinc.workforceplusplus.Notifications.NotificationManager;
import com.example.ddnbinc.workforceplusplus.Utilities.FabPresenter;
import com.example.ddnbinc.workforceplusplus.Utilities.ImageUploader;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,FragmentManager.OnBackStackChangedListener {

    private DataBaseConnectionPresenter dataBaseConnectionPresenter;
    private Employee employee;
    private FabPresenter fabPresenter;
    private Menu menu;

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

        menu=navigationView.getMenu();

        FloatingActionButton floatingActionButton = (FloatingActionButton)findViewById(R.id.fab);
        fabPresenter = new FabPresenter(floatingActionButton,this);

        fabPresenter.Hide();

        dataBaseConnectionPresenter = new DataBaseConnectionPresenter();

        Bundle bundle = getIntent().getExtras();

        System.out.println(System.currentTimeMillis()/1000);

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
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().add(R.id.content_frame,new Login()).commit();
            }

        }
        else{
            FragmentManager fragmentManager = getFragmentManager();
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
        return dataBaseConnectionPresenter;
    }

    public void setUserFcmToken(){
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.FCM_PREF), Context.MODE_PRIVATE);
        String token =sharedPreferences.getString(getString(R.string.FCM_TOKEN),"");
        if(!token.equals("")) dataBaseConnectionPresenter.getDbReference().child("Users").child(dataBaseConnectionPresenter.getUID()).
                child("fcmToken").setValue(token);
    }

    public void setEmployee(Employee e){
        employee=e;
    }
    public void showFab(){
     fabPresenter.Show();
    }
    public void hideFab(){
        fabPresenter.Hide();
    }
    public Employee getEmployee(){
        return employee;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem menuItem = menu.getItem(0);
        menuItem.setVisible(false); // dont need this yet.

        return true;
    }
    public void hideStatusBar(){
        getSupportActionBar().hide();
    }
    public void showStatusBar(){
        getSupportActionBar().show();
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
        menu.findItem(R.id.Pending).setVisible(false);// set logout and login respectively
    }
    public void showManagerView(){
        menu.findItem(R.id.Pending).setVisible(true);// set logout and login respectively
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK) {
            if (requestCode == 1) {
                ImageUploader imageUploader = new ImageUploader(dataBaseConnectionPresenter,employee.getEmployeeId(),this);
                String path= imageUploader.getPath(this,data.getData());
                imageUploader.setFilePath(path);
                imageUploader.UploadImage();
            }
        }
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
            bundle.putBoolean("type",true);
            Shift shift = new Shift();
            shift.setArguments(bundle);
            fragmentManager .beginTransaction().replace(R.id.content_frame, shift,"Shift").commit();
        } else if (id == R.id.ViewShift) {
            bundle.putBoolean("type",false);
            Shift shift = new Shift();
            shift.setArguments(bundle);
            fragmentManager .beginTransaction().replace(R.id.content_frame, shift).commit();
        } else if (id == R.id.Logout) {
            FirebaseAuth.getInstance().signOut();
            fragmentManager.beginTransaction().replace(R.id.content_frame,new Login()).commit();
        }else if (id == R.id.Pending) {
            fragmentManager .beginTransaction().replace(R.id.content_frame,new PendingShifts(),"PendingShift").commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FirebaseAuth.getInstance().signOut();
    }
}
