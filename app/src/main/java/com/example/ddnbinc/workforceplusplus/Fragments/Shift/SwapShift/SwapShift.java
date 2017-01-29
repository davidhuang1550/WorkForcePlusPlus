package com.example.ddnbinc.workforceplusplus.Fragments.Shift.SwapShift;

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
import android.widget.Button;

import com.example.ddnbinc.workforceplusplus.DataBaseConnection.DataBaseConnectionPresenter;
import com.example.ddnbinc.workforceplusplus.Fragments.Shift.replaceable_shift;
import com.example.ddnbinc.workforceplusplus.MainActivity;
import com.example.ddnbinc.workforceplusplus.R;
import com.example.ddnbinc.workforceplusplus.Classes.GivenUpShift;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 * Created by david on 2017-01-23.
 */
//604800
public class SwapShift extends Fragment implements FragmentManager.OnBackStackChangedListener,
        View.OnClickListener{

    private Activity mActivity;
    private static View MainView;
    private static SwipeRefreshLayout refreshLayout;
    private replaceable_shift replaceableShift;
    private Stack<HashMap<String,ArrayList<GivenUpShift>>> shifts;
    private Button NextWeek;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fragmentManager= getFragmentManager();
        fragmentManager.addOnBackStackChangedListener(this);
        mActivity=getActivity();
    }
    public static View getMainView(){
        return MainView;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MainView= inflater.inflate(R.layout.swap_shift_page,container,false);
        ((MainActivity)mActivity).setTitle("Swap Shifts");
        refreshLayout = (SwipeRefreshLayout)MainView.findViewById(R.id.swiperefresh);
        NextWeek = (Button)MainView.findViewById(R.id.week_button);
        shifts= new Stack<>();
        NextWeek.setOnClickListener(this);

        ((MainActivity)mActivity).showFab();
        init();


        return MainView;
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

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.week_button){
            shifts.add(replaceableShift.getShift());
            replaceableShift.onDestroy();
            Bundle bundle =new Bundle();
            bundle.putLong("Start",replaceableShift.getEndTime());
            setValues(bundle);
            init(bundle);
        }
    }
    public void setValues(Bundle bundle){
        SwapShiftPresenter swapShiftPresenter = new SwapShiftPresenter(mActivity,refreshLayout,MainView);

        bundle.putSerializable("swap",(Serializable)swapShiftPresenter);
        bundle.putBoolean("Clickable",true);
    }

    /*
    if the user presses the next week button
     */
    public void init(Bundle bundle){
        replaceableShift = new replaceable_shift();
        replaceableShift.setArguments(bundle);
        init_replaceable_shift(replaceableShift);
    }

    public void init(){
        replaceableShift = new replaceable_shift();
        Bundle bundle = new Bundle();
        setValues(bundle);

        replaceableShift.setArguments(bundle);


        init_replaceable_shift(replaceableShift);
    }
    public void init_replaceable_shift(replaceable_shift replaceableShift){
        FragmentManager fragmentManager = mActivity.getFragmentManager();
        fragmentManager.beginTransaction().add(R.id.replaceable_frame,replaceableShift).commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((MainActivity)mActivity).hideFab();
    }
}
