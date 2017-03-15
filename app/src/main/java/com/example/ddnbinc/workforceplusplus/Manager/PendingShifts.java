package com.example.ddnbinc.workforceplusplus.Manager;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.ddnbinc.workforceplusplus.MainActivity;
import com.example.ddnbinc.workforceplusplus.R;

/**
 * Created by davidhuang on 2017-02-19.
 */

public class PendingShifts extends Fragment implements FragmentManager.OnBackStackChangedListener{

    private View myView;
    private Activity mActivity;
    private PendingShiftsPresenter pendingShiftsPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fragmentManager= getFragmentManager();
        fragmentManager.addOnBackStackChangedListener(this);
        mActivity=getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.pending_shifts,container,false);
        ((MainActivity)mActivity).setTitle("Pending Shifts");
        ListView listView = (ListView)myView.findViewById(R.id.listview);
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)myView.findViewById(R.id.swiperefresh);
        pendingShiftsPresenter = new PendingShiftsPresenter(mActivity,listView,swipeRefreshLayout,myView);
        pendingShiftsPresenter.fetchShifts();


        return myView;
    }
    /*
    maybe for future use if this gets bigger
     */
    public void init(){

    }
    @Override
    public void onBackStackChanged() {
        final DrawerLayout drawer = (DrawerLayout) mActivity.findViewById(R.id.drawer_layout);
        Toolbar toolbar = (Toolbar) mActivity.findViewById(R.id.toolbar);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(mActivity, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        if (((MainActivity) mActivity).getFragmentManager().getBackStackEntryCount() > 0) {
            ((MainActivity) mActivity).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity) mActivity).onBackPressed();
                }
            });
        } else {
            ((MainActivity) mActivity).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            toggle.syncState();
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    drawer.openDrawer(GravityCompat.START);
                }
            });
        }
    }

}
