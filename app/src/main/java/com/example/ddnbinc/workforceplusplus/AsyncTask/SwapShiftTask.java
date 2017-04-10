package com.example.ddnbinc.workforceplusplus.AsyncTask;

import com.example.ddnbinc.workforceplusplus.Fragments.Shift.SwapShift.SwapShiftModel;
import com.example.ddnbinc.workforceplusplus.Classes.GivenUpShift;
import com.example.ddnbinc.workforceplusplus.Utilities.StringFormater;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * Created by david on 2017-01-23.
 */

public class SwapShiftTask{

    private LinkedHashMap<String , ArrayList<GivenUpShift>> shifts;
    private Long time_now;
    private Long time_diff;
    private Query query;
    private SwapShiftModel swapShiftModel;

    public SwapShiftTask(SwapShiftModel swap, Query q,Long time_d,Long time_n){
        shifts= new LinkedHashMap<>();
        swapShiftModel=swap;
        query=q;
        time_now=time_d;
        time_diff=time_n;
    }

    public void Execute() {
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    Iterable iterator = dataSnapshot.getChildren();
                    Iterator loop = iterator.iterator();
                    time_now+=86400;
                    ArrayList<GivenUpShift> tempshifts = new ArrayList<GivenUpShift>();
                    DataSnapshot dataSnapshotShift = null;
                    GivenUpShift givenUpShift= null;
                    boolean next = true;

                    while(loop.hasNext() ||next==false){
                        if(next) {
                            dataSnapshotShift=(DataSnapshot)loop.next();
                            givenUpShift = dataSnapshotShift.getValue(GivenUpShift.class);
                            givenUpShift.setShiftId(dataSnapshotShift.getKey());
                        }
                        try {
                            if(givenUpShift.getStartTime()<=time_now){
                                tempshifts.add(givenUpShift);
                                next=true;
                                if(!loop.hasNext()){
                                    cloneobj(tempshifts,time_now);
                                    time_now+=86400;
                                }

                            }
                            else{
                                cloneobj(tempshifts,time_now);
                                time_now+=86400;
                                next=false;
                                if(time_now>time_diff)break;
                            }
                        }catch (ClassCastException e){
                            e.printStackTrace();
                        }

                    }
                    if(shifts.size()<7){
                        for(int i= shifts.size(); i<7;i++){
                            shifts.put(StringFormater.getmInstance().setDays(time_now),tempshifts);
                            time_now+=86400;
                        }
                    }
                    swapShiftModel.setEndTime(time_diff);
                    swapShiftModel.setValues(shifts);



                } catch (ArrayIndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    public synchronized void  cloneobj(ArrayList<GivenUpShift> temp,Long time){
        ArrayList<GivenUpShift> into= new ArrayList<GivenUpShift>();
        for(GivenUpShift s : temp){
            into.add(s);
        }
        shifts.put(StringFormater.getmInstance().setDays(time),into);

        temp.clear();

    }
}

