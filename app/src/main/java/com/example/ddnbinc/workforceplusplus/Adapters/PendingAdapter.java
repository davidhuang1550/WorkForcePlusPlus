package com.example.ddnbinc.workforceplusplus.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ddnbinc.workforceplusplus.MainActivity;
import com.example.ddnbinc.workforceplusplus.Manager.ShiftInfoPresenter;
import com.example.ddnbinc.workforceplusplus.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by davidhuang on 2017-02-19.
 */

public class PendingAdapter extends BaseAdapter {
    private Activity mActivity;
    private ArrayList<String> PendingShifts;
    private ArrayList<String> PendingShiftsTaker;

    public PendingAdapter(Activity activity, ArrayList<String> strings,ArrayList<String> taker){
        mActivity=activity;
        PendingShifts=strings;
        PendingShiftsTaker=taker;
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
        final TextView textView = (TextView)row.findViewById(R.id.Email_id);
        ((MainActivity)mActivity).getDataBaseConnectionPresenter().getDbReference().child("EmployeeKeyToName").child(text)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        textView.setText(dataSnapshot.getValue(String.class)+" wants to take a shift from another employee.");
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ShiftInfoPresenter shiftInfoPresenter= new ShiftInfoPresenter(mActivity,PendingShifts.get(i),PendingShiftsTaker.get(i));
                shiftInfoPresenter.setView();
            }
        });



        return row;
    }
}
