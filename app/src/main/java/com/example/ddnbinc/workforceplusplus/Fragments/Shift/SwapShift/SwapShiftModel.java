package com.example.ddnbinc.workforceplusplus.Fragments.Shift.SwapShift;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ddnbinc.workforceplusplus.AsyncTask.SwapShiftTask;
import com.example.ddnbinc.workforceplusplus.DataBaseConnection.DataBaseConnectionPresenter;
import com.example.ddnbinc.workforceplusplus.Dialogs.Default.ProgressBarPresenter;
import com.example.ddnbinc.workforceplusplus.R;
import com.example.ddnbinc.workforceplusplus.Users.GivenUpShift;

import java.util.ArrayList;
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

    public SwapShiftModel(Activity a, DataBaseConnectionPresenter data, SwipeRefreshLayout swipe, View v){
        mActivity= a;
        dataBaseConnectionPresenter=data;
        swipeRefreshLayout=swipe;
        myView=v;
        responseNum=0;
        tableLayout= (TableLayout)myView.findViewById(R.id.layouttable);
    }

    public void init(){
        final SwapShiftTask swapShiftTask = new SwapShiftTask(dataBaseConnectionPresenter,mActivity,this);

        Handler handler = new Handler();

        swipeRefreshLayout.setRefreshing(true);

        swapShiftTask.execute();
        Runnable userthread= new Runnable() {
            @Override
            public void run() {
                if(swapShiftTask.getStatus()== AsyncTask.Status.RUNNING){
                    swapShiftTask.cancel(true);
                    Toast.makeText(mActivity,"Error has occured ",Toast.LENGTH_SHORT).show();
                }
            }
        };
        handler.postDelayed(userthread,5000);

    }
    public void setValues(HashMap<String, ArrayList<GivenUpShift>> shifts){
        int counter =0;
        Iterator iterator = shifts.entrySet().iterator();
        while (iterator.hasNext()){
            counter++;
            Map.Entry pair = (Map.Entry)iterator.next();
            for(GivenUpShift temp : (ArrayList<GivenUpShift>)pair.getValue()){
                TableRow tableRow = Process(temp);
                tableLayout.addView(tableRow,responseNum+counter);
                responseNum++;
            }

        }
        swipeRefreshLayout.setRefreshing(false);
    }
    public TableRow Process(GivenUpShift givenUpShift){
        if(givenUpShift.getEmployeeId()!=null) {
            Long Start_time = (givenUpShift.getStartTime() % 86400);
            Long Start_Hour = Start_time / 3600;
            Long Start_minutes = (Start_time % 3600) / 60;

            String Start_AmPM;
            if (Start_time >= 43200) {
                Start_AmPM = "PM";
            } else {
                Start_AmPM = "AM";
            }

            Long End_time = (givenUpShift.getEndTime() % 86400);
            Long End_Hour = Start_time / 3600;
            Long End_minutes = (Start_time % 3600) / 60;
            String End_AmPM;
            if (End_time >= 43200) {
                End_AmPM = "PM";
            } else {
                End_AmPM = "AM";
            }

            TableRow tableRow = AddRow(Start_Hour.toString() + ":" + Start_minutes + Start_AmPM, End_Hour.toString() + ":" + End_minutes + End_AmPM);
            return tableRow;
        }else{
            return AddRow("No","Shifts");
        }



    }

    public TableRow AddRow(String start, String end){
        TableRow tr = new TableRow(mActivity);
        TextView textView = new TextView(mActivity);

        textView.setId(mActivity.getResources().getIntArray(R.array.options)[responseNum]);
        textView.setText(start + " - " + end);
        textView.setBackgroundResource(R.drawable.shift_time_view);
        textView.setTextColor(Color.BLACK);
        textView.setClickable(true);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT,1f);
        tr.addView(textView,params);

        return tr;
    }
}
