package com.example.ddnbinc.workforceplusplus.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ddnbinc.workforceplusplus.Manager.ShiftInfoPresenter;
import com.example.ddnbinc.workforceplusplus.R;

import java.util.ArrayList;

/**
 * Created by davidhuang on 2017-02-19.
 */

public class PendingAdapter extends BaseAdapter {
    private Activity mActivity;
    private ArrayList<String> PendingShifts;
    private ArrayList<String> PendingShiftsTaker;
    private View mView;

    public PendingAdapter(Activity activity, ArrayList<String> strings,ArrayList<String> taker,View view){
        mActivity=activity;
        PendingShifts=strings;
        PendingShiftsTaker=taker;
        mView=view;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public int getCount() {
        return PendingShifts.size();
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View row;
        final LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.pending_shifts_component,null);
        final String text = PendingShiftsTaker.get(i);
        TextView textView = (TextView)row.findViewById(R.id.Email_id);
        textView.setText(text+" wants to take a shift from another employee.");

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ShiftInfoPresenter shiftInfoPresenter= new ShiftInfoPresenter(mActivity,PendingShifts.get(i),PendingShiftsTaker.get(i));
                shiftInfoPresenter.setView(mView);
            }
        });



        return row;
    }
}
