package com.example.ddnbinc.workforceplusplus.Fragments.Shift;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.os.Parcelable;
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

import com.example.ddnbinc.workforceplusplus.Classes.Users.Manager;
import com.example.ddnbinc.workforceplusplus.DataBaseConnection.DataBaseConnectionPresenter;
import com.example.ddnbinc.workforceplusplus.Fragments.Shift.SwapShift.SwapShiftPresenter;
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
 * Controls The View for both self shifts as well as swap shifts.
 */
//604800
public class Shift extends Fragment implements FragmentManager.OnBackStackChangedListener,
        View.OnClickListener{

    private Activity mActivity;
    private static View MainView;
    private static SwipeRefreshLayout refreshLayout;
    private replaceable_shift replaceableShift;
    private boolean two_weeks;
    private Stack<HashMap<String,ArrayList<GivenUpShift>>> shifts;
    private Button NextWeek;
    private Button PreviousWeek;
    private Bundle ViewDeterminer;


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

      //  Bundle bundle = getArguments();
        ViewDeterminer = getArguments();
        ((MainActivity)mActivity).setTitle(ViewDeterminer.getString("name"));

        refreshLayout = (SwipeRefreshLayout)MainView.findViewById(R.id.swiperefresh);

        refreshLayout.setEnabled(false);
        NextWeek = (Button)MainView.findViewById(R.id.week_button);
        PreviousWeek=(Button)MainView.findViewById(R.id.previous_button);
        shifts= new Stack<>();
        NextWeek.setOnClickListener(this);
        PreviousWeek.setOnClickListener(this);
        PreviousWeek.setClickable(false);
        ((MainActivity)mActivity).showFab();
        two_weeks=true;


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

        Bundle bundle =new Bundle();
        if(view.getId()==R.id.week_button){
            //without this if, it will cause error with single previous,next
            Long time = (two_weeks)?replaceableShift.getEndTime():replaceableShift.getEndTime()+604800;
            two_weeks=true;
            shifts.add(replaceableShift.getShift());
            bundle.putInt("TYPE",R.string.NEXT_WEEK);
            defaults(time,bundle);
            PreviousWeek.setBackgroundResource(R.drawable.swap_shift_button);
            PreviousWeek.setClickable(true);
        }else if(view.getId()==R.id.previous_button){
            Long time = (two_weeks)?replaceableShift.getEndTime()-1209600:replaceableShift.getEndTime()-604800;
            two_weeks=false;//
            bundle.putSerializable("Previous",shifts.peek());
            shifts.pop();
            if(shifts.size()<1){
                PreviousWeek.setBackgroundResource(R.drawable.swap_shift_gray_button);
                PreviousWeek.setClickable(false);
            }
            bundle.putInt("TYPE",R.string.PREVIOUS_WEEK);
            defaults(time,bundle);

        }

    }
    /*
    both functions call this.
     */
    public void defaults(Long time,Bundle bundle){
        bundle.putLong("Start",time);
        replaceableShift.onDestroy();
        setValues(bundle);
        init(bundle);
    }
    /*
    user shifts
     */
    public void setSelfValues(Bundle b){
        ((MainActivity)mActivity).hideFab();
        SwapShiftPresenter swapShiftPresenter = new SwapShiftPresenter(mActivity,refreshLayout,MainView);
        b.putSerializable("swap",(Serializable)swapShiftPresenter);
        b.putString("Employee",((MainActivity)mActivity).getEmployee().getEmployeeId());
        b.putBoolean("Clickable",false);
    }
    /*
    given up shifts
     */
    public void setValues(Bundle bundle){
        SwapShiftPresenter swapShiftPresenter = new SwapShiftPresenter(mActivity,refreshLayout,MainView);
        bundle.putParcelable("swap",(Parcelable) swapShiftPresenter);
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
    /*
    initial
    */
    public void init(){
        replaceableShift = new replaceable_shift();
        Bundle bundle = new Bundle();
        bundle.putInt("TYPE",R.string.INIT_LOAD);
        Boolean b = ViewDeterminer.getBoolean("type");
        if(b){
            setValues(bundle);
        }else setSelfValues(bundle);


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
