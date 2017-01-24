package com.example.ddnbinc.workforceplusplus.Fragments.Shift.SwapShift;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ddnbinc.workforceplusplus.DataBaseConnection.DataBaseConnectionPresenter;
import com.example.ddnbinc.workforceplusplus.MainActivity;
import com.example.ddnbinc.workforceplusplus.R;

/**
 * Created by david on 2017-01-23.
 */
//604800
public class SwapShift extends Fragment{

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
        myView= inflater.inflate(R.layout.swap_shift_page,container,false);
        ((MainActivity)mActivity).setTitle("Swap Shifts");
        dataBaseConnectionPresenter= ((MainActivity)mActivity).getDataBaseConnectionPresenter();

        SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout)myView.findViewById(R.id.swiperefresh);
        swapShiftPresenter = new SwapShiftPresenter(mActivity,dataBaseConnectionPresenter,refreshLayout,myView);
        swapShiftPresenter.init();


        return myView;
    }

}
