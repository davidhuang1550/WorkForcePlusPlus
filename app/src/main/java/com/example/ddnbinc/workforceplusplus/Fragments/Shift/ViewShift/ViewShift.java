package com.example.ddnbinc.workforceplusplus.Fragments.Shift.ViewShift;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ddnbinc.workforceplusplus.MainActivity;
import com.example.ddnbinc.workforceplusplus.R;

/**
 * Created by david on 2017-01-23.
 */

public class ViewShift extends Fragment implements View.OnClickListener{

    private Activity mActivity;
    private View myView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity= getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.view_shift_page,container,false);
        mActivity.setTitle("View Shift");
        ((MainActivity)mActivity).showStatusBar();

        return myView;
}

    @Override
    public void onClick(View view) {

    }
}
