package com.example.ddnbinc.workforceplusplus.Fragments.Shift.SwapShift;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.BundleCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.ddnbinc.workforceplusplus.AsyncTask.SwapShiftTask;
import com.example.ddnbinc.workforceplusplus.DataBaseConnection.DataBaseConnectionPresenter;
import com.example.ddnbinc.workforceplusplus.Fragments.Shift.Confirm_GivenUpShift;
import com.example.ddnbinc.workforceplusplus.MainActivity;
import com.example.ddnbinc.workforceplusplus.R;
import com.example.ddnbinc.workforceplusplus.Classes.GivenUpShift;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by david on 2017-01-23.
 */

public class SwapShiftModel {
    private Activity mActivity;
    private DataBaseConnectionPresenter dataBaseConnectionPresenter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private View myView;
    private int  responseNum;
    private TableLayout tableLayout;
    private HashMap<String,ArrayList<GivenUpShift>> shifts;
    private Long endTime;
    private View MainView;

    public SwapShiftModel(Activity a, DataBaseConnectionPresenter data, SwipeRefreshLayout swipe, View v,View main){
        mActivity= a;
        dataBaseConnectionPresenter=data;
        swipeRefreshLayout=swipe;
        myView=v;
        responseNum=0;
        tableLayout= (TableLayout)myView.findViewById(R.id.layouttable);
        MainView=main;
    }


    /*
    can simplfy this some more
     */
    public void next_init(){
        Long End = endTime+604800;

        setDateHeader(endTime,End);
        final SwapShiftTask swapShiftTask = new SwapShiftTask(dataBaseConnectionPresenter,this,endTime,End);

        swipeRefreshLayout.setRefreshing(true);

        swapShiftTask.Execute();
    }
    public void init(){
        Long start=(System.currentTimeMillis() / 1000);
        Long End = start+604800;

        setDateHeader(start,End);

        SwapShiftTask swapShiftTask = new SwapShiftTask(dataBaseConnectionPresenter,this,start,End);

        swipeRefreshLayout.setRefreshing(true);

        swapShiftTask.Execute();
    }
    public void setDateHeader(Long seconds_begin,Long seconds_end ){
        TextView textView = (TextView)MainView.findViewById(R.id.work_week);
        SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");

        String dateString_begin = formatter.format(new Date(seconds_begin * 1000L));
        String dateString_end = formatter.format(new Date(seconds_end * 1000L));

        textView.setText(dateString_begin+"-"+dateString_end);
    }



    public void setEndTime(Long t){
        endTime=t;
    }
    public Long getEndTime(){
        return endTime;
    }
    public void procVal(GivenUpShift givenUpShift,int counter){
        TableRow tableRow = Process(givenUpShift);
        tableLayout.addView(tableRow,responseNum+counter);
        responseNum++;
    }
    public void setDateDay(int count,String date){
        TextView textview=null;
        switch(count){
            case 1:
                textview = (TextView)myView.findViewById(R.id.day1);
                break;
            case 2:
                textview = (TextView)myView.findViewById(R.id.day2);
                break;
            case 3:
                textview = (TextView)myView.findViewById(R.id.day3);
                break;
            case 4:
                textview = (TextView)myView.findViewById(R.id.day4);
                break;
            case 5:
                textview = (TextView)myView.findViewById(R.id.day5);
                break;
            case 6:
                textview = (TextView)myView.findViewById(R.id.day6);
                break;
            case 7:
                textview = (TextView)myView.findViewById(R.id.day7);
                break;
        }
        if(textview!=null)textview.setText(date);


    }
    
    
    public void setValues(HashMap<String, ArrayList<GivenUpShift>> shift){
        shifts=shift;
        int counter =0;
        Iterator iterator = shifts.entrySet().iterator();
        int responseNum_before;
        while (iterator.hasNext()){
            counter++;
            Map.Entry pair = (Map.Entry)iterator.next();

            responseNum_before=responseNum;
            setDateDay(counter,pair.getKey().toString());
            for(GivenUpShift temp : (ArrayList<GivenUpShift>)pair.getValue()){
                procVal(temp,counter);
            }
            if(responseNum_before==responseNum){
                procVal(new GivenUpShift(),counter);
            }


        }
        swipeRefreshLayout.setRefreshing(false);
    }
    public String combine(Long hour, Long minutes, Long time){
       String AMPM;
        if (time >= 43200) {
            AMPM = " PM";
            if(time>=46800)hour-=12;
        } else AMPM = " AM";
        return  hour.toString()+":"+minutes.toString()+AMPM;
    }

    public TableRow Process(GivenUpShift givenUpShift){
        if(givenUpShift.getShiftId()!=null) {
            Long Start_time = (givenUpShift.getStartTime() % 86400);
            Long End_time = (givenUpShift.getEndTime() % 86400);

            String start = combine(Start_time / 3600,(Start_time % 3600) / 60,Start_time);
            String end = combine(End_time / 3600,(End_time % 3600) / 60,End_time);

            TableRow tableRow = AddRow(start, end,givenUpShift);
            return tableRow;
        }else{
            return AddRow("No","Shifts",null);
        }



    }
    public HashMap<String,ArrayList<GivenUpShift>> getWeekShift(){
        return shifts;
    }

    public TableRow AddRow(String start, String end,final GivenUpShift givenUpShift){
        TableRow tr = new TableRow(mActivity);
        final TextView textView = new TextView(mActivity);

        textView.setId(mActivity.getResources().getIntArray(R.array.options)[responseNum]);
        textView.setText(start + " - " + end);
        textView.setBackgroundResource(R.drawable.shift_time_view);
        textView.setTextColor(Color.BLACK);
        textView.setClickable(true);
        if(givenUpShift!=null) {
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Bundle bunlde = new Bundle();
                    bunlde.putSerializable("GivenUpShift", (Serializable) givenUpShift);
                    bunlde.putString("Times", textView.getText().toString());

                    Confirm_GivenUpShift confirm_givenUpShift = new Confirm_GivenUpShift();
                    confirm_givenUpShift.setArguments(bunlde);

                    FragmentTransaction transaction = ((MainActivity) mActivity).getFragmentManager().beginTransaction();
                    transaction.setCustomAnimations(R.animator.enter_anim, R.animator.exit_anim, R.animator.enter_anim_back, R.animator.exit_anime_back);
                    transaction.add(R.id.content_frame, confirm_givenUpShift).addToBackStack("Posts").commit();

                }
            });
        }
        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT,1f);
        tr.addView(textView,params);

        return tr;
    }
}
