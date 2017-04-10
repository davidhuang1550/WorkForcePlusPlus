package com.example.ddnbinc.workforceplusplus.Fragments.Shift;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ddnbinc.workforceplusplus.Classes.GivenUpShift;
import com.example.ddnbinc.workforceplusplus.DataBaseConnection.DataBaseConnectionPresenter;
import com.example.ddnbinc.workforceplusplus.Fragments.Shift.SwapShift.SwapShiftPresenter;
import com.example.ddnbinc.workforceplusplus.MainActivity;
import com.example.ddnbinc.workforceplusplus.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by davidhuang on 2017-01-28.
 */

public class replaceable_shift extends Fragment {
    private Activity mActivity;
    private View myView;
    private DataBaseConnectionPresenter dataBaseConnectionPresenter;
    private SwapShiftPresenter swapShiftPresenter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity=getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView= inflater.inflate(R.layout.swap_shift_view_frame,container,false);

        dataBaseConnectionPresenter= ((MainActivity)mActivity).getDataBaseConnectionPresenter();

        Bundle bundle = getArguments();
        if(bundle!=null &&dataBaseConnectionPresenter!=null){
            int OPTION = bundle.getInt("TYPE",R.string.INIT_LOAD);

            try {



                swapShiftPresenter = (SwapShiftPresenter) bundle.getParcelable("swap");
                swapShiftPresenter.setClickable(bundle.getBoolean("Clickable"));
                String employeeid = bundle.getString("Employee");
                swapShiftPresenter.setDatabase(dataBaseConnectionPresenter);
                swapShiftPresenter.setView(myView);

                Long temp = bundle.getLong("Start");

                switch(OPTION) {
                    case R.string.PREVIOUS_WEEK:
                        HashMap<String,ArrayList<GivenUpShift>> hashMap = (HashMap<String,ArrayList<GivenUpShift>>)
                                bundle.getSerializable("Previous");

                        swapShiftPresenter.setTime(temp);
                        setTime(temp);
                        swapShiftPresenter.setValues(hashMap);
                        break;

                    case R.string.NEXT_WEEK:
                        swapShiftPresenter.setTime(temp);
                        setTime(temp);
                        swapShiftPresenter.init(employeeid);
                        break;
                    case R.string.INIT_LOAD:
                        Long time = ((System.currentTimeMillis() / 1000)-86400);
                        swapShiftPresenter.setTime(time);
                        setTime(time);
                        swapShiftPresenter.init(employeeid);
                        break;
                }




            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }


        return myView;
    }
    public void setTime(Long temp){
        swapShiftPresenter.setDateHeader();
    }

    public HashMap<String,ArrayList<GivenUpShift>> getShift(){
        return swapShiftPresenter.getWeekShift();
    }
    public Long getEndTime(){
        return swapShiftPresenter.getTime();
    }

    @Override
    public void onDestroy() {
        ViewGroup container = (ViewGroup)mActivity.findViewById(R.id.replaceable_frame);
        if(container!=null)container.removeAllViews();
        super.onDestroy();
    }


}
