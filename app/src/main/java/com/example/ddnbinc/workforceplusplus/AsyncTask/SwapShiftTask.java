package com.example.ddnbinc.workforceplusplus.AsyncTask;

import android.app.Activity;
import android.os.AsyncTask;

import com.example.ddnbinc.workforceplusplus.DataBaseConnection.DataBaseConnectionPresenter;
import com.example.ddnbinc.workforceplusplus.Dialogs.Default.ProgressBarPresenter;
import com.example.ddnbinc.workforceplusplus.Fragments.Shift.SwapShift.SwapShift;
import com.example.ddnbinc.workforceplusplus.Fragments.Shift.SwapShift.SwapShiftModel;
import com.example.ddnbinc.workforceplusplus.Users.GivenUpShift;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by david on 2017-01-23.
 */

public class SwapShiftTask extends AsyncTask<Void,Void,Void> {

    private DataBaseConnectionPresenter dataBaseConnectionPresenter;
    private Activity mActivity;
    private HashMap<String , ArrayList<GivenUpShift>> shifts;
    private String[] days = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
    private int count;

    private SwapShiftModel swapShiftModel;

    public SwapShiftTask(DataBaseConnectionPresenter d, Activity a, SwapShiftModel swap){
        dataBaseConnectionPresenter=d;
        mActivity=a;
        shifts= new HashMap<>();
        swapShiftModel=swap;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Long time_now = (System.currentTimeMillis() / 1000);
        Long time_diff=(System.currentTimeMillis() / 1000)+86400;
        for(count=0; count<7; count++){
            dataBaseConnectionPresenter.getDbReference().child("GivenUpShifts").orderByChild("StartTime").startAt(time_now).
                    endAt(time_diff).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(!dataSnapshot.hasChildren()){
                        shifts.put(days[count],new ArrayList<GivenUpShift>());
                    }
                    else{
                        ArrayList<GivenUpShift> s = new ArrayList<GivenUpShift>();
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            GivenUpShift temp_shift = snapshot.getValue(GivenUpShift.class);
                            s.add(temp_shift);
                        }
                        shifts.put(days[count],s);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



        }


        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        swapShiftModel.setValues(shifts);
    }
}
