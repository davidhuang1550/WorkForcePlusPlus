package com.example.ddnbinc.workforceplusplus.Fragments.Shift.ViewShift;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ddnbinc.workforceplusplus.Classes.GivenUpShift;
import com.example.ddnbinc.workforceplusplus.Fragments.Shift.SwapShift.SwapShiftPresenter;
import com.example.ddnbinc.workforceplusplus.Fragments.Shift.replaceable_shift;
import com.example.ddnbinc.workforceplusplus.MainActivity;
import com.example.ddnbinc.workforceplusplus.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 * Created by david on 2017-01-23.
 * CURRENTLY NOT IN USE
 */

public class ViewShift extends Fragment implements View.OnClickListener{

    private Activity mActivity;
    private View myView;
    private replaceable_shift replaceableShift;
    private SwipeRefreshLayout refreshLayout;
    private Stack<HashMap<String,ArrayList<GivenUpShift>>> shifts;
    private Button NextWeek;
    private Button PreviousWeek;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity= getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.swap_shift_page,container,false);
        mActivity.setTitle("View Shift");
        refreshLayout = (SwipeRefreshLayout)myView.findViewById(R.id.swiperefresh);
        ((MainActivity)mActivity).showStatusBar();
        PreviousWeek=(Button)myView.findViewById(R.id.previous_button);
        NextWeek = (Button)myView.findViewById(R.id.week_button);
        shifts= new Stack<>();
        NextWeek.setOnClickListener(this);
        PreviousWeek.setOnClickListener(this);
        PreviousWeek.setClickable(false);
        init();


        return myView;
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
    public void init(Bundle bundle){
        replaceableShift = new replaceable_shift();

        replaceableShift.setArguments(bundle);
        init_replaceable_shift(replaceableShift);
    }

    public void setValues(Bundle b){
        SwapShiftPresenter swapShiftPresenter = new SwapShiftPresenter(mActivity,refreshLayout,myView);
        b.putSerializable("swap",(Serializable)swapShiftPresenter);
        b.putString("Employee",((MainActivity)mActivity).getEmployee().getEmployeeId());
        b.putBoolean("Clickable",false);
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
}
