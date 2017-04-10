package com.example.ddnbinc.workforceplusplus.Fragments.Shift.SwapShift;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.ddnbinc.workforceplusplus.AsyncTask.SwapShiftTask;
import com.example.ddnbinc.workforceplusplus.DataBaseConnection.DataBaseConnectionPresenter;
import com.example.ddnbinc.workforceplusplus.Dialog.GiveUpConfirmation;
import com.example.ddnbinc.workforceplusplus.Fragments.Shift.Confirm_GivenUpShift;
import com.example.ddnbinc.workforceplusplus.MainActivity;
import com.example.ddnbinc.workforceplusplus.R;
import com.example.ddnbinc.workforceplusplus.Classes.GivenUpShift;
import com.example.ddnbinc.workforceplusplus.Utilities.StringFormater;
import com.google.firebase.database.Query;

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

public class SwapShiftModel implements  Serializable{

    private final static int oneWeek=604800;

    private Activity mActivity;
    private DataBaseConnectionPresenter dataBaseConnectionPresenter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private View myView;
    private int  responseNum;
    private TableLayout tableLayout;
    private HashMap<String,ArrayList<GivenUpShift>> shifts;
    private Long _startTime;
    private View MainView;
    private boolean clickable;

    /**
     *
     * @param a Activity, probably going to be mainactivity
     * @param swipe SwipeViewLayout resfresher for the purpose of stopping the refreshing button
     * @param main the view in which we must populate the tablelayout with rows.
     */
    public SwapShiftModel(Activity a, SwipeRefreshLayout swipe,View main){
        mActivity= a;
        swipeRefreshLayout=swipe;
        responseNum=0;
        MainView=main;
    }

    /**
     *
     * @param data the database connection singleton
     */
    public void setDatabase(DataBaseConnectionPresenter data){
        dataBaseConnectionPresenter=data;
    }

    /**
     *
     * @param view if we did not set the view already from the constructor or we must change the view
     *              then we can set the view dynamically
     */
    public void setView(View view){
        myView=view;
        tableLayout= (TableLayout)myView.findViewById(R.id.layouttable);
    }

    /**
     *
     * @param b if true, we are in the swap shift view we can use normal click listeners
     *          otherwise use the long click listener.
     */
    public void setClickable(boolean b){
        clickable=b;
    }

    /**
     * This function is generally used for swap shifts so we dont need a reference key to the employee
     * @param t1 start time
     * @param t2 end time
     * @return database querying query
     */
    public Query setSwap(Long t1, Long t2){
        return dataBaseConnectionPresenter.getDbReference().child("GivenUpShifts").orderByChild("startTime").
                startAt(t1).endAt(t2);
    }

    /**
     * used to fetch shifts of the employee logged in
     * @param t1 start time
     * @param t2 end time
     * @param employee employee reference key
     * @return database querying query
     */
    public Query setShift(Long t1, Long t2,String employee){
        return dataBaseConnectionPresenter.getDbReference().child("Users").child(employee).child("Shifts").orderByChild("startTime").
                startAt(t1).endAt(t2);

    }

    /**
     *
     * @param employee reference key
     */
    public void init(String employee){
        Long End = _startTime+oneWeek;
        Query query;
        query = (employee == null ) ? setSwap(_startTime,End) : setShift(_startTime,End,employee);

        SwapShiftTask swapShiftTask = new SwapShiftTask(this, query, _startTime, End);
        swipeRefreshLayout.setRefreshing(true);

        swapShiftTask.Execute();
    }

    public void setDateHeader(){
        TextView textView = (TextView)MainView.findViewById(R.id.work_week);

        textView.setText(StringFormater.getmInstance().setHeader(_startTime+86400,_startTime+oneWeek));
    }


    /**
     *
     * @param t set the begining time
     */
    public void setEndTime(Long t){
        _startTime=t;
    }

    /**
     *
     * @return begin time
     */
    public Long getEndTime(){
        return _startTime;
    }

    /**
     * this function adds one shift to the row based on the given shift as well as the
     * place to put the row in the table
     * @param givenUpShift object that contains the generic info (start time - endtime)
     * @param counter the place where we must put the row in the tablelayout
     */
    public void procVal(GivenUpShift givenUpShift,int counter){
        TableRow tableRow = Process(givenUpShift);
        tableLayout.addView(tableRow,responseNum+counter);
        responseNum++;
    }

    /**
     * this function sets the date ie (Monday, Tuesday) etc... of the static table rows
     * that define the days
     * @param count which day are we setting
     * @param date the actual day ie (Monday, Tuesday) etc..
     */
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

    /**
     * This object takes all the shifts and begins to populate the table
     * @param shift specifies the list of shifts for each day.
     */
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

    /**
     * Create the row ,populate it and return it to be added.
     * @param givenUpShift shift object
     * @return the row created
     */
    public TableRow Process(GivenUpShift givenUpShift){
        if(givenUpShift.getShiftId()!=null) {

            TableRow tableRow = AddRow(StringFormater.getmInstance().ConvertTimeString(givenUpShift.getStartTime()),
                    StringFormater.getmInstance().ConvertTimeString(givenUpShift.getEndTime()),givenUpShift);
            return tableRow;
        }else{
            return AddRow("No","Shifts",null);
        }



    }

    /**
     * THis function gets called by the fragment. Uses a momento type pattern to store the states
     * of the previous dates so we dont need to recache all the dates but instead just repopulate.
     * @return the shifts objects
     */
    public HashMap<String,ArrayList<GivenUpShift>> getWeekShift(){
        return shifts;
    }

    /**
     *
     * @param start start time
     * @param end end time
     * @param givenUpShift actual shift object is needed once the row is clicked for more info
     * @return the row object created
     */
    public TableRow AddRow(String start, String end,final GivenUpShift givenUpShift){
        TableRow tr = new TableRow(mActivity);
        final TextView textView = new TextView(mActivity);

        textView.setId(mActivity.getResources().getIntArray(R.array.options)[responseNum]);
        textView.setText(start + " - " + end);
        textView.setBackgroundResource(R.drawable.shift_time_view);
        textView.setTextColor(Color.BLACK);
        textView.setClickable(true);
        if(givenUpShift!=null && clickable) {
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
                    transaction.add(R.id.content_frame, confirm_givenUpShift).addToBackStack("Shifts").commit();

                }
            });
        }
        else if(givenUpShift!=null && !clickable){
            textView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    GiveUpConfirmation confirmationDialog = new GiveUpConfirmation();

                    Bundle bundle= new Bundle();
                    bundle.putSerializable("Shift",givenUpShift);

                    confirmationDialog.setArguments(bundle);
                    confirmationDialog.show(((MainActivity)mActivity).getFragmentManager(),"Alert Dialog Fragment");

                    return false;
                }
            });
        }
        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT,1f);
        params.setMargins(15,5,15,5);

        tr.addView(textView,params);

        return tr;
    }
}
