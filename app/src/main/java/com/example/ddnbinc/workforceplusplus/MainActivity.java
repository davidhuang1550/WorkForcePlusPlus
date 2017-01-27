package com.example.ddnbinc.workforceplusplus;

import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ddnbinc.workforceplusplus.DataBaseConnection.DataBaseConnectionPresenter;
import com.example.ddnbinc.workforceplusplus.Dialogs.Default.ProgressBarPresenter;
import com.example.ddnbinc.workforceplusplus.Fragments.Authentication.Login.GetUserPresenter;
import com.example.ddnbinc.workforceplusplus.Fragments.Authentication.Login.Login;
import com.example.ddnbinc.workforceplusplus.Fragments.Shift.SwapShift.SwapShift;
import com.example.ddnbinc.workforceplusplus.Fragments.Shift.ViewShift.ViewShift;
import com.example.ddnbinc.workforceplusplus.Classes.Users.Employee;
import com.example.ddnbinc.workforceplusplus.Notifications.NotificationManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,FragmentManager.OnBackStackChangedListener {

    private DataBaseConnectionPresenter dataBaseConnectionPresenter;
    private Employee employee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


       // FirebaseAuth.getInstance().signOut();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fab.hide(); // using this to give up shifts

        dataBaseConnectionPresenter = new DataBaseConnectionPresenter();

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){

            ProgressBarPresenter progressBarPresenter = new ProgressBarPresenter(this,"Loading");
            progressBarPresenter.Show();

            NotificationManager notificationManager = new NotificationManager(this,bundle.getBundle("Info"),progressBarPresenter);
            GetUserPresenter getUserPresenter= new GetUserPresenter(dataBaseConnectionPresenter,this,progressBarPresenter);
            getUserPresenter.setFlag(notificationManager);
            getUserPresenter.getUser();

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
                child("FcmToken").setValue(token);
    }

    public void setEmployee(Employee e){
        employee=e;
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        FragmentManager fragmentManager = getFragmentManager();
        int id = item.getItemId();

        if (id == R.id.Profile) {
            // Handle the camera action
        } else if (id == R.id.Barcode) {

        } else if (id == R.id.Mail) {

        } else if (id == R.id.SwapShift) {
            fragmentManager .beginTransaction().replace(R.id.content_frame,new SwapShift(),"SwapShift").commit();
        } else if (id == R.id.ViewShift) {
            fragmentManager .beginTransaction().replace(R.id.content_frame,new ViewShift()).commit();

        } else if (id == R.id.Logout) {

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
